(ns excercise1.simplex
  (:use [clojure.core.matrix]))


(def memory (atom 0))

(def example-array [[#{:blue :circle} #{:blue :hexagon}]
                    [#{:red :circle} #{:blue :circle}]])


(def example-query (fn [pos] (do (Thread/sleep 500)
                                 (and (:blue pos) (:circle pos)))))

(defn execute-query [query array]
  (doseq [row array
          cell row]
    (and (query cell) (swap! memory inc))))

(defn execute-task [task picture time-available]
  (let [f (future (execute-query task picture))]
    (do (deref f time-available nil) @memory)))