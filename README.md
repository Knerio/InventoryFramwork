
# Welcome to the InventoryFramework by Derio

## Installation
	
To use the Framework you have to install it via a Repository and a Dependency

**Repository**
  ```
  <repository>
    <id>inventory</id>
    <url>https://nexus.derioo.de/nexus/content/repositories/InventoryFramwork</url>
  </repository>
```
#
**Dependency**

```
<dependency>
  <groupId>de.derioo</groupId>
  <artifactId>InventoryFramwork</artifactId>
  <version>VERSION</version>
</dependency>

```
Current Version: 1.0-RELEASE

## Getting started

To create GUI´s and more you first have to initialize the Framework in the onEnable method in your plugin

```java
new InventoryFramework(plugin);
```

## Simple GUI

To create a basic GUI in e. g. in a CMD you can use the following

```java

InventoryFramework.builder()  
	.setup("Title", 3*9)  
	.provider(new InventoryProvider() {  
		@Override  
		public void init(Player player, InventoryContents contents) {  
		SmartItem smartItem = SmartItem.get(new ItemStack(Material.DIAMOND), (event, item) -> {  
			player.sendMessage("You have clicked a diamond");  
			item.setItem(new ItemStack(Material.DIRT));
		});  
		contents.fillBorders(smartItem);  
		}  
	})  
	.build()  
	.open(player);

```

This will create a GUI with 3 rows, the borders are filled with diamonds, and if you click it the item changes to dirt and it will send you a message


# Animations


## Item Animations

To create a Item Animation you have to call in your init method the followings
```java
@Override  
public void init(Player player, InventoryContents contents) {  
	SmartItem smartItem = SmartItem.get(new ItemStack(Material.DIAMOND), (event, item) -> {  
		player.sendMessage("You have clicked a diamond");  
		item.setItem(new ItemStack(Material.DIRT));  
	});  
	contents.fillBorders(smartItem);  
  
	InventoryFramework.invAnimation(contents.getBuilder())  //Create the animation
		.prepare(InventoryAnimation.AnimationType.HORIZONTAL_LEFT_RIGHT)   //set the type
		.start(5, InventoryAnimation.TimeUnit.SECONDS,   //set the duration
			new InventoryAnimation.AnimationItem(SmartItem.get(Material.CHEST), 6, 10))  //Set the items
		.cycleInfinite()  //set it infinite
		.hideAfterAnimation();  //Hides the item after the animation
}
```
	
This will create a chest at slot 10, wich animates 6 slots to the right
	



