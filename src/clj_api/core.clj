(ns clj-api.core
  (:require [clj-api.config :as config]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]
            [clj-api.components.example-component :as example-component])
  (:gen-class))

(defn respond-hello [request]
  {:status 200 :body "Hello, world!"})

(def routes
  (route/expand-routes
   #{["/greet" :get respond-hello :route-name :greet]}))

(defn create-server [config]
  (http/create-server
   {::http/routes routes
    ::http/type :jetty
    ::http/join? false
    ::http/port (-> config :server :port)}))

(defn start [config]
  (http/start (create-server config)))

(defn system
  [config]
  (component/system-map
   :example-component (example-component/new-example-component config)))

(defn -main
  []
  (let [system (-> (config/read-config)
                   (system)
                   (component/start-system))]
    (println "Starting the API server... ")
    (.addShutdownHook (Runtime/getRuntime)
                      (Thread. #(component/stop-system system)))
    #_(println "Starting the API with config: " config)))
