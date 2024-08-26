(ns clj-api.components.example-component
  (:require [com.stuartsierra.component :as component])
  (:gen-class))

(defrecord ExampleComponent
    [config]
  component/Lifecycle

  (start [component]
    (println ";; Starting Example Component")
    ;; Return an updated version of the component with
    ;; the run-time state assoc'd in.
    (assoc component :state ::started))

  (stop [component]
    (println ";; Stopping Example Component")
    ;; Return the component, optionally modified. Remember that if you
    ;; dissoc one of a record's base fields, you get a plain map.
    (assoc component :state nil)))

(defn new-example-component
  [config]
  (map->ExampleComponent {:config config}))
