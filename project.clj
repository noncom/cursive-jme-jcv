(defproject cursive-jcv "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

            ; Strange formatting here. The tab is not correct.
            :repositories [["jme3" "http://updates.jmonkeyengine.org/maven/"]]

            :main cursive-jcv.core

  :dependencies [[org.clojure/clojure "1.6.0"]

                 ; - JavaCV
                 [org.bytedeco/javacv "0.11"]
                 [org.bytedeco/javacpp "0.11"]
                 [org.bytedeco.javacpp-presets/ffmpeg "2.6.1-0.11"]
                 [org.bytedeco.javacpp-presets/opencv "2.4.11-0.11"]

                 [com.jme3/jme3-core "3.0.10"]
                 [com.jme3/jme3-effects "3.0.10"]
                 [com.jme3/jme3-networking "3.0.10"]
                 [com.jme3/jme3-plugins "3.0.10"]
                 [com.jme3/jme3-jogg "3.0.10"]
                 [com.jme3/jme3-terrain "3.0.10"]
                 [com.jme3/jme3-blender "3.0.10"]
                 [com.jme3/jme3-jbullet "3.0.10"]
                 [com.jme3/jme3-bullet "3.0.10"]
                 [com.jme3/jme3-bullet-natives "3.0.10"]
                 [com.jme3/jme3-bullet-natives-android "3.0.10"]
                 [com.jme3/jme3-desktop "3.0.10"]
                 [com.jme3/jme3-lwjgl "3.0.10"]

                 [com.jme3/jme3-android "3.0.10"]
                 [com.jme3/jme3-ios "3.0.10"]

                 [com.jme3-lib/lwjgl-natives "2.9.0"]
                 [com.jme3-lib/lwjgl "2.9.0"]
                 [com.jme3-lib/oggd "1.0.0"]
                 [com.jme3-lib/jinput "2.0.0-b01"]
                 [com.jme3-lib/jbullet "3.0.10-jme"]
                 ]
            :java-source-paths ["java"])
