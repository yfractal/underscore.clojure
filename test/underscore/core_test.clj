(ns underscore.core-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [underscore.core :refer :all]))

(defn f-with-0-argument [])
(defn f-with-1-argument [a])
(defn f-with-2-argument [b b])

(defn map-f [x] x)
(defn map-f-with-i [x i] (* x i))

(fact "know how many arguments the a function needs"
      (num-of-arguments f-with-1-argument) => 1
      (num-of-arguments f-with-2-argument) => 2)

(facts "_map"
       (fact "_map can work as map"
             (_map map-f [1 2 3]) => [1 2 3])
       (fact "_map can work as map-indexed"
             (_map map-f-with-i [0 1 2]) => [0 1 4 ]))
