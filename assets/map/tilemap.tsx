<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.4" tiledversion="1.4.3" name="tilemap" tilewidth="16" tileheight="16" tilecount="12" columns="6">
 <image source="tilemap.png" width="96" height="32"/>
 <tile id="0">
  <properties>
   <property name="speed" type="float" value="1"/>
  </properties>
 </tile>
 <tile id="1">
  <properties>
   <property name="icy" type="bool" value="true"/>
   <property name="speed" type="float" value="1"/>
  </properties>
 </tile>
 <tile id="2">
  <properties>
   <property name="speed" type="float" value="1.5"/>
  </properties>
 </tile>
 <tile id="3">
  <properties>
   <property name="speed" type="float" value="1.5"/>
  </properties>
 </tile>
 <tile id="4">
  <properties>
   <property name="speed" type="float" value="1.5"/>
  </properties>
 </tile>
 <tile id="6">
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="16" height="16"/>
  </objectgroup>
 </tile>
 <tile id="7">
  <properties>
   <property name="disappearAfterStart" type="bool" value="true"/>
  </properties>
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="16" height="16"/>
  </objectgroup>
 </tile>
 <tile id="8">
  <properties>
   <property name="speed" type="float" value="2"/>
  </properties>
 </tile>
 <tile id="9">
  <properties>
   <property name="speed" type="float" value="2"/>
  </properties>
 </tile>
 <tile id="10">
  <properties>
   <property name="speed" type="float" value="0.5"/>
  </properties>
 </tile>
 <tile id="11">
  <properties>
   <property name="speed" type="float" value="0.5"/>
  </properties>
 </tile>
</tileset>
