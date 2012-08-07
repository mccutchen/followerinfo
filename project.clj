(defproject followerinfo "0.1.0-SNAPSHOT"
  :description "Show information about the followers of one or more Twitter accounts"
  :url "https://github.com/mccutchen/followerinfo"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.cli "0.2.1"]
                 [org.clojure/tools.logging "0.2.3"]
                 [clj-http "0.5.2"]
                 [cheshire "4.0.1"]]
  :main followerinfo.core)
