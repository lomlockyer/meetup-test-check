(ns meetup-test-check.core
  (:require [inet.data.ip :as ip]))

(defn custom-sort [coll]
  (sort coll))

(defn ip-allowed? [ip]
  (ip/network-contains? "0.0.0.0/0" ip))

(defn ipv6-allowed? [ip]
  (ip/network-contains? "0:0:0:0:0:0:0:0/0" ip))

(defn ip-any-allowed? [ip]
  (or (ip/network-contains? "0.0.0.0/0" ip)
      (ip/network-contains? "0:0:0:0:0:0:0:0/0" ip)))