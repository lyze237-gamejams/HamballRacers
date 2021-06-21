<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.4" tiledversion="1.4.3" name="tilemap" tilewidth="16" tileheight="16" tilecount="4" columns="2">
 <image source="tilemap.png" width="32" height="32"/>
 <tile id="1">
  <properties>
   <property name="slippery" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="2">
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="16" height="16"/>
  </objectgroup>
 </tile>
</tileset>
