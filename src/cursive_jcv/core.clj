(ns cursive-jcv.core
  (:import (com.jme3.app SimpleApplication)
           (com.jme3.scene.shape Box)
           (com.jme3.scene Geometry)
           (com.jme3.material Material)
           (cursive_jcv JavaCVPlayer)
           (com.jme3.texture Texture2D)
           (com.jme3.math ColorRGBA)))


(def texture (atom nil))
(def player (atom nil))
(def bxs (atom nil))

(defn create-player []
  (reset! texture (Texture2D. 800 600 com.jme3.texture.Image$Format/RGB8))
  (reset! player (JavaCVPlayer.)))

(defn create-box [geom x y z s]
  (let [box (.clone geom)]
    (.setLocalTranslation box x y z)
    (.setLocalScale box s s s)
    box))

(defn r [] (range -5 5))

;; !!! Strange formatting below. Too big tabs. How to make it tabs in 2 spaces whatever is there?

(defn create-boxes [app sample-geom]
  (let [boxes (mapv
                (fn [x] (mapv
                          (fn [y] (mapv
                                    (fn [z] (create-box sample-geom x y z (java.lang.Math/atan2 x y))) (r)))
                          (r)))
                (r))
        boxes (flatten boxes)]
    (reset! bxs boxes)
    (doseq [box boxes] (.attachChild (.getRootNode app) box))))

(defn create-scene [app]
  (let [k (/ 1 640)
        mesh (Box. (* k 640) (* k 360) (* k 640))
        geom (Geometry. "Video Box" mesh)
        mat (Material. (.getAssetManager app) "Common/MatDefs/Misc/Unshaded.j3md")]
    (.setTexture mat "ColorMap" @texture)
    (.setColor mat "Color" (ColorRGBA. 1 1 1 1))
    (.setMaterial geom mat)
    ;(reset! box geom)
    ;(.setLocalTranslation geom 0 0 -10)
    ;(.attachChild (.getRootNode app) geom)

    (create-boxes app geom)
    ))

(defn play-video []
  (let [new-image (.loadFile @player "assets/tribocycle-196657796.mp4")]
    (println "file loaded")
    (.setImage @texture new-image)
    (println "image set: " new-image)
    (.play @player)
    (println "play on")))

(defn mk-app []
  (proxy [SimpleApplication] []
    (simpleInitApp []
      (create-player)
      (create-scene this)
      (play-video))
    (simpleUpdate [tpf]
      (.play @player tpf)
      (doseq [box @bxs] (.rotate box (* tpf 1) (* tpf 1.1) (* tpf 1.3))))))

(defn -main [& args]
  (let [app (mk-app)]
    (.start app)))