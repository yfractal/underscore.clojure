(ns underscore.core-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [underscore.core :refer :all]
            [underscore.test-helpers :refer :all]))

(fact "know how many arguments the a function needs"
             (num-of-arguments f-with-1-argument) => 1
             (num-of-arguments f-with-2-argument) => 2)

