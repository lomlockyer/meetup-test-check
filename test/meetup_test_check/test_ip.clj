(ns meetup-test-check.test-ip
  (:require [meetup-test-check.core :as c]
            [clojure.test.check.generators :as gen]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]))

(def ip-gen (gen/fmap #(clojure.string/join "." %)
                      (gen/tuple (gen/choose 0 255)
                                 (gen/choose 0 255)
                                 (gen/choose 0 255)
                                 (gen/choose 0 255))))


(def ipv6-gen (gen/fmap #(clojure.string/join ":" %)
                        (gen/fmap #(map (fn [x] (format "%X" x)) %) (gen/tuple (gen/choose 0 65535)
                                                                               (gen/choose 0 65535)
                                                                               (gen/choose 0 65535)
                                                                               (gen/choose 0 65535)
                                                                               (gen/choose 0 65535)
                                                                               (gen/choose 0 65535)
                                                                               (gen/choose 0 65535)
                                                                               (gen/choose 0 65535)))))

(def any-gen (gen/one-of [ip-gen ipv6-gen]))

(defspec ip-address-check
         10000
         (prop/for-all [ip ip-gen]
                       (c/ip-allowed? ip)))

(defspec ip6-address-check
         10000
         (prop/for-all [ip ipv6-gen]
                       (c/ipv6-allowed? ip)))

(defspec any-address-check
         10000
         (prop/for-all [ip ipv6-gen]
                       (c/ip-any-allowed? ip)))