(ns clj-api.config
  (:require [aero.core :as aero])
  (:gen-class))

(defn read-config
  "Reads config from classpath"
  []
  (-> "config.edn"
      clojure.java.io/resource
      aero/read-config))

(read-config)
