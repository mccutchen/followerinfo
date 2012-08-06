;; (ns followerinfo.core
;;   (:use clojure.tools.cli)
;;   (:gen-class))

(ns followerinfo.core
  (:require [cheshire.core :as json]
            [clojure.tools.cli :as cli]
            [clojure.tools.logging :as log]
            [clj-http.client :as client])
  (:gen-class))

(def twitter-api-base "http://api.twitter.com/1")

(defn twitter-api
  [endpoint params]
  (let [url (str twitter-api-base endpoint)
        resp (client/get url {:query-params params :as :json})]
    (:body resp)))

(defn fetch-follower-ids
  "Returns a list of user IDs of the followers of the given Twitter handle"
  ([handle]
     (fetch-follower-ids handle -1 []))
  ([handle cursor acc]
     (printf "Fetching followers of %s (cursor: %s; count: %d)\n"
             handle cursor (count acc))
     (let [resp (twitter-api "/followers/ids.json" {:screen_name handle :cursor cursor})
           ids (:ids resp)
           next_cursor (:next_cursor resp)]
       (if (> next_cursor 0)
         (recur handle next_cursor (concat acc ids))
         (concat acc ids)))))

(defn -main
  [& args]
  (let [[options args banner]
        (cli/cli args
             ["-h" "--help" "Show help" :default false :flag true]
             ["-v" "--verbose" "Show detailed info about each follower"
              :default false :flag true])]
    (when (:help options)
      (println banner)
      (System/exit 0))
    (let [handle (first args)
          follower-ids (fetch-follower-ids handle)]
      (println)
      (printf "@%s has %d followers\n" handle (count follower-ids)))))
