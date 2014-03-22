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

(fact "know how many arguments the a function needs"
      (num-of-arguments f-with-1-argument) => 1
      (num-of-arguments f-with-2-argument) => 2)

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

; 1 get value
(facts "_dispatch, do different operation by the arg-num"
       (let [f0 (fn [] 123)
             f1 (fn [x] x)
             f2 (fn [x y] (+ x y))]
        (fact "arg-num = 0, execulate fisrt function"
              (_dispatch 0 f0 "" "") => (f0))
        (fact "1, the third"
              (_dispatch 1 f0 (f1 10) "") => (f1 10))
        (fact "2, the third"
              (_dispatch 2 f0 "" (f2 1 2)) => 3)))
