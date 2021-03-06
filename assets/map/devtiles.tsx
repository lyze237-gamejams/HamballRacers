<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.4" tiledversion="1.4.3" name="tilemap" tilewidth="16" tileheight="16" tilecount="18" columns="6">
 <image source="devtiles.png" width="96" height="48"/>
 <tile id="0">
  <properties>
   <property name="speed" type="float" value="0.75"/>
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
 <tile id="5">
  <properties>
   <property name="music" value="fastPad"/>
   <property name="speed" type="float" value="2"/>
   <property name="speedMultiplierTime" type="float" value="3"/>
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
   <property name="music" value="fastPad"/>
   <property name="speed" type="float" value="2"/>
  </properties>
 </tile>
 <tile id="9">
  <properties>
   <property name="music" value="fastPad"/>
   <property name="speed" type="float" value="2"/>
  </properties>
 </tile>
 <tile id="10">
  <properties>
   <property name="music" value="slowPad"/>
   <property name="speed" type="float" value="0.5"/>
  </properties>
 </tile>
 <tile id="11">
  <properties>
   <property name="music" value="slowPad"/>
   <property name="speed" type="float" value="0.5"/>
  </properties>
 </tile>
 <tile id="12">
  <properties>
   <property name="chargeNitro" type="bool" value="true"/>
   <property name="music" value="chargePad"/>
  </properties>
 </tile>
 <tile id="14">
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="16" height="16"/>
  </objectgroup>
 </tile>
 <tile id="15">
  <properties>
   <property name="speed" type="float" value="0.35"/>
  </properties>
 </tile>
 <tile id="17">
  <properties>
   <property name="forceSpeedMultiplierPenalty" type="bool" value="true"/>
   <property name="music" value="slowPad"/>
   <property name="speed" type="float" value="0.5"/>
   <property name="speedMultiplierTime" type="float" value="5"/>
  </properties>
 </tile>
</tileset>
