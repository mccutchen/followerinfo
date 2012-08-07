(ns followerinfo.core
  (:require [clojure.set :refer (union intersection)]
            [cheshire.core :as json]
            [clojure.tools.cli :as cli]
            [clojure.tools.logging :as log]
            [clj-http.client :as client])
  (:gen-class))

(def twitter-api-base "http://api.twitter.com/1")

(defn twitter-api
  "Makes a call to the Twitter API and returns the decoded response body."
  [endpoint params]
  (log/infof "Twitter API: %s %s" endpoint params)
  (let [url (str twitter-api-base endpoint)
        resp (client/get url {:query-params params :as :json})]
    (:body resp)))

(defn fetch-follower-ids
  "Returns a set of user IDs of the followers of the given user."
  ([handle]
     (fetch-follower-ids handle -1 []))
  ([handle cursor acc]
     (let [resp (twitter-api "/followers/ids.json" {:screen_name handle :cursor cursor})
           ids (:ids resp)
           next_cursor (:next_cursor resp)]
       (if (> next_cursor 0)
         (recur handle next_cursor (concat acc ids))
         (set (concat acc ids))))))

(defn fetch-follower-infos
  "Returns a lazy seq of maps of user info for the given IDs."
  ([ids]
     (fetch-follower-infos ids []))
  ([ids acc]
     (if (empty? ids)
       acc
       (let [these-ids (take 100 ids)
             rest-ids (drop 100 ids)
             infos (twitter-api "/users/lookup.json" {:user_id (apply str (interpose "," these-ids))})]
         (recur rest-ids (concat acc infos))))))

(defn make-formatter
  "Returns a function suitable for formatting each follower info record
  according to whether we want verbose output."
  [verbose?]
  (if verbose?
    (fn [rec] (println (json/encode rec)))
    (fn [{:keys [screen_name id]}] (printf "%s,%s\n" screen_name id))))

(defn -main
  [& args]
  (let [[options args banner]
        (cli/cli args
             ["-h" "--help" "Show help" :default false :flag true]
             ["-v" "--[no-]verbose" "Show detailed info about each follower" :default false]
             ["-i" "--[no-]intersection" "Show intersection of followers (use --no-intersection to show union)" :default true])]
    (when (:help options)
      (println banner)
      (System/exit 0))
    (let [flattener (if (:intersection options) intersection union)
          follower-ids (apply flattener (map fetch-follower-ids args))
          followers (fetch-follower-infos follower-ids)
          formatter (make-formatter (:verbose options))]
      ;; need to use doseq because followers is a lazy seq and map wouldn't
      ;; realize it.
      (doseq [rec followers] (formatter rec)))))
