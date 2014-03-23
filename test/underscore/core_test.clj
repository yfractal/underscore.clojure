(ns underscore.core-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [underscore.core :refer :all]))

(defn f-with-0-argument [])
(defn f-with-1-argument [a])
(defn f-with-2-argument [b b])

(defn map-f [x] x)
(defn map-f-with-i [x i] (* x i))
(defn add [x y] (+ x y))
(defn add-with-index [x y i] (+ i (add x y)))

(facts "_map"
       (fact "_map can work as map"
             (_map map-f [1 2 3]) => [1 2 3])
       (fact "_map can work as map-indexed"
             (_map map-f-with-i [0 1 2]) => [0 1 4 ]))

(facts "_reduce"
       (facts "with defualt value"
              (fact "as reduce"
                    (_reduce add 0 [0 1 2]) => 3)
              (fact "with index"
                    (_reduce add-with-index 0 [0 1 2])=> 6))
       (facts "wihout default value"
              (fact "as reduce"
                    (_reduce add [0 1 2]) => 3)
              (fact "with index"
                    (_reduce add-with-index [0 1 2]) => 4)))

(facts "_dispatch, do different operation by fun's arg-num"
       (let [f0 (fn [] 123)
             f1 (fn [x] x)
             f2 (fn [x y] (+ x y))]
         (fact "call f0"
               (_dispatch2 (map f1 [1]) "") => (map f1 [1]))
         (fact "run arg ,try form 2"
               (_dispatch2 (map f0 [1]) (map f1 [1])) => (map f1 [1])
               )
         ))
