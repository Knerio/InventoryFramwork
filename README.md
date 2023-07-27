# Welcome to the InventoryFramework by Derio

## Installation
	
To use the Framework you have to install it via a Repository and a Dependency

**Repository**
  ```
  <repository>
    <id>inventory</id>
    <url>https://nexus.derioo.de/nexus/content/repositories/inventory</url>
  </repository>
```

**Dependency**

```
<dependency>
  <groupId>de.derioo</groupId>
  <artifactId>InventoryFramwork</artifactId>
  <version>VERSION</version>
</dependency>

```

#

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

This will create a GUI with 3 rows, the borders are filled with diamonds, and if you click it the item changes to dirt


	
	
	



