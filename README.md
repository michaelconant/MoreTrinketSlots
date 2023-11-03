# More Trinket Slots
<img src="https://github.com/project-curiosity/MoreTrinketSlots/blob/main/src/main/resources/preview.png" width=25% height=25%><br>
This is a mod for Necesse that adds customizable options that allow you to get more or less trinket slots than the game typically allows by changing which bosses drop items to increase the amount of trinket slots. By default the mod is setup to let you get a max of 8 trinket slots.

Change the mod settings to make the game easier or harder. Make the game easier by making it possible to obtain more trinket slots than the vanilla game. Or make the game harder by making it so obtain less trinket slots than the vanilla game. Change the mod settings to fit your playstyle.

Note: This mod should be completely safe to use on existing saves. However, to be safe you should always backup your world when using a new mod your world.

## Features at a glance:
- ability to change how many trinket slots you start with
- ability to change how many trinket slots can be obtained by setting which bosses drop items to increase trinket slots (and by how much)
- adds 20 items that can be configured to give more trinket slots
  - 11 are themed around bosses in the game
  - 9 are generic items that are intended to be used with other mods
- adds 4 unobtainable items to add/remove trinket slots
- adds 1 unobtainable sword to instantly kill mobs (for testing)
- adds a file to each world save file that is used to configure the mod for each world
- adds 3 commands to configure the mod and check it's current settings
- changes the broker price of all items that increase the amount of trinket slots to 100
  - this is currently due to a game limitation of not being able to change the broker price of items once the game has loaded
  - if I find a way, I plan on adding the broker price of each item to the mod's setting file and changing the broker price of each item when the save is loaded
- adds textures for dectrinkets and inctrinkets

## Default Settings
By default this mod is set so the player starts with 4 trinkets slots and can get a max of 8 trinket slots. To see details how check Default under the Preset section below.

## New Items
<img src="https://github.com/project-curiosity/MoreTrinketSlots/blob/main/src/main/images/allitems.png" width=10% height=10%><br>
These are the names of the items in the order they appear in the image above:
- Belt Clip, Spider Leg Keychain, Protective Case, Pocket Protector, Belt Bag, Trinket Pouch, Charm Bracelet
- Swamp Fruit, Dragon Satchel, Dry Bag, Sling Bag, How To Hold More Trinkets: Vol. 1-3
- How To Hold More Trinkets: Vol. 4-9
- moretrinketslots_dectrinkets10, moretrinketslots_dectrinkets5, dectrinkets, inctrinkets, moretrinketslots_inctrinkets5, moretrinketslots_inctrinkets10, Test Sword

## Commands
This mod adds 3 commands:

### `MTS.info <slots/bosses/items/all>`
- Outputs information about the mod's settings to chat for the user.
- Permission Level: User
- 4 Parameter Options
  - `slots`
    - tells the user the trinket slots they start with and the max amount they can get
  - `bosses`
    - tell the user which bosses drop items to increase trinket slots and their condition for doing so
  - `items`
    - tells the user all the items that increase trinket slots and how much slots they give
  - `all`
    - tells the user the slots, bosses, and items information

### `MTS.trinketslots <player/all/online/offline> <set/reset/max/adjust> <new amount>`
- Used to change the amount of trinket slots for the target.
- Permission Level: Owner
- Note: Items in trinket slots that are remove because of this will be lost.
- 4 options for who to target:
  - `player`
    - a specified online player, cannot target offline player
  - `offline`
    - all players who are currently offline
  - `online`
      - all players who are currently online
  - `all`
      - every player that has ever joined the server
- 4 options for how to change trinket slots size
  - `set`
      - sets the target's trinket slot size to the specified new amount
  - `reset`
    - sets the target's trinket slot size to the initial trinket slots setting
  - `max`
    - sets the target's trinket slot size to the max possible based on the settings for the boss drops and items
  - `adjust`
    - changes the target's trinket slot size to be within the initial starting size and max possible size
      - if the target has less than the initial starting size they will be set the initial starting size
      - if the target has more than the max size they will be set to the max size
      - if the target's trinket slot size is between the initial and max size their trinket slot size will not change
- optional new amount parameter 
  - only used by `set` option

### `MTS.loadpreset <default/vanilla/nogain/zerotohero> <subpreset number>`
- Changes the mod settings to a built-in preset.
- Permission Level: Owner
- Note: When changing presets it is recommended to use `MTS.trinketslots all adjust` or `MTS.trinketslots all reset` to ensure all players have a trinket slot amount that fits within the preset's restrictions.
- 4 preset options
  - `default`
  - `vanilla`
  - `nogain`
  - `zerotohero`
  - These presets are explained below but for specific can be found below in the Presets section.
- optional `subpreset number` parameter
  - This parameter is used by `nogain` and `zerotohero` to get slightly different variations of these presets.

## Presets

### Default (default)
Changes the game to make it possible to get 8 trinket slots.
- Empty Pendant
  - Dropped By: Void Wizard
  - Sets trinket slots to 5
- Pocket Protector
  - Dropped By: Ancient Vulture
  - Sets trinket slots to 6
- Charm Bracelet
  - Dropped By: Cryo Queen
  - Sets trinket slots to 7
- Fallen Wizard
  - Dropped By: Wizard Socket
  - Sets trinket slots to 8

### Vanilla (vanilla)
Preset that uses the same exact settings as the base game so you get a "vanilla" experience. Useful if you want access to the commands to change slots but don't want to actually change the game.

### No Gain (nogain)
Players can not gain more trinket slots, what they start with is what they get. Removes all boss drops that give trinket slots and changes items to give no slots. By default it is set to 4 trinket slots but you can use the optional `subpreset number` parameter to set the amount of trinket slots you get. For example you could use `MTS.loadpreset nogain 1` so players only have 1 trinket slot and there are no drops to get more.

### Zero to Hero (zerotohero)
Players start with 0 trinket slots and more bosses drop items to increase trinket slot size. There are six sub presets for this that can be chosen by entering 4, 6, 8, 10, 11, or 12 for the optional `subpreset number` parameter. These change drops so the max slots that can be obtained are 4, 6, 8, 10, 11, or 12. By default it choses the 6 preset. The subpresets are shown below.

#### 4
- Belt Clip
  - Dropped By: Evil's Protector
  - Sets trinket slots to 1
- Protective Case
  - Dropped By: Swamp Guardian
  - Sets trinket slots to 2
- Charm Bracelet
  - Dropped By: Cryo Queen
  - Sets trinket slots to 3
- Wizard Socket
  - Dropped By: Fallen Wizard
  - Sets trinket slots to 4

#### 6
- Belt Clip
  - Dropped By: Evil's Protector
  - Sets trinket slots to 1
- Empty Pendant
  - Dropped By: Void Wizard
  - Sets trinket slots to 2
- Pocket Protector
  - Dropped By: Ancient Vulture
  - Sets trinket slots to 3
- Trinket Pouch
  - Dropped By: Reaper
  - Sets trinket slots to 4
- Swamp Fruit
  - Dropped By: Pest Warden
  - Sets trinket slots to 5
- Wizard Socket
  - Dropped By: Fallen Wizard
  - Sets trinket slots to 6

#### 8
Makes is so about every other boss pre Fallen Wizard drops an item to increase trinket slots by 1.
- Belt Clip
  - Dropped By: Evil's Protector
  - Sets trinket slots to 1
- Spider Leg Keychain
  - Dropped By: Queen Spider
  - Sets trinket slots to 2
- Empty Pendant
  - Dropped By: Void Wizard
  - Sets trinket slots to 3
- Protective Case
  - Dropped By: Swamp Guardian
  - Sets trinket slots to 4
- Pocket Protector
  - Dropped By: Ancient Vulture
  - Sets trinket slots to 5
- Trinket Pouch
  - Dropped By: Reaper
  - Sets trinket slots to 6
- Swamp Fruit
  - Dropped By: Pest Warden
  - Sets trinket slots to 7
- Wizard Socket
  - Dropped By: Fallen Wizard
  - Sets trinket slots to 8

#### 10/11/12
Makes it so basically every boss drops an item to increase trinket slots by one. 11 and 12 are the same as the 10 preset except they include drops for the post Fallen Wizard bosses.
- Belt Clip
  - Dropped By: Evil's Protector
  - Sets trinket slots to 1
- Spider Leg Keychain
  - Dropped By: Queen Spider
  - Sets trinket slots to 2
- Empty Pendant
  - Dropped By: Void Wizard
  - Sets trinket slots to 3
- Protective Case
  - Dropped By: Swamp Guardian
  - Sets trinket slots to 4
- Pocket Protector
  - Dropped By: Ancient Vulture
  - Sets trinket slots to 5
- Trinket Pouch
  - Dropped By: Reaper
  - Sets trinket slots to 6
- Charm Bracelet
  - Dropped By: Cryo Queen
  - Sets trinket slots to 7
- Swamp Fruit
  - Dropped By: Pest Warden
  - Sets trinket slots to 8
- Dragon Satchel
  - Dropped By: Sage and Grit
  - Sets trinket slots to 9
- Wizard Socket
  - Dropped By: Fallen Wizard
  - Sets trinket slots to 10

If 11 or 12 is chosen there is also:
- Dry Bag
  - Dropped By: Mother Slime
  - Sets trinket slots to 11

If 12 is chosen there is also:
- Sling Bag
  - Dropped By: Night Swarm
  - Sets trinket slots to 12

## Settings File
You can configure the mod yourself by modifying the settings file that it creates. This mod creates a file called `mtsSettings.cfg` in each save file. This file is used to load settings for this mod. This makes it so each save file can have their own settings. Save files on windows can be found at `C:\Users\Your Username\AppData\Roaming\Necesse\saves`. Save files are stored as zip files, so to edit the settings file you will need an application to view and edit files inside zip files. When viewing the file it should look something like this:
```
MORETRINKETSLOTS = {
	initialSlots = 4,
	bossdrops = {
		fallenwizard = {
			itemID = wizardsocket,
			minSlots = 6,
			maxSlots = 6,
			increment = 1
		},
		voidwizard = {
			itemID = emptypendant,
			minSlots = 5,
			maxSlots = 5,
			increment = 1
		}
	}
}
```
- `initialSlots` is the amount of slots players should start with
- `bossdrops` is a list of each boss that drops items and each listing has details about that item that drops from them
- `itemID` is the itemID of the item that should drop from the boss
- `minSlots` is the lowest amount of slots the item will ever set the player to
  - in the example above the wizard socket will always set the players trinket slots to 6 when they are below or at 5
- `maxSlots` the maximum amount of slots that the can item can give and is also used for the condition for the boss drop
  - if the player is under the `maxSlots` then the item will drop from the boss
  - if the player is at or above `maxSlots` the item will not drop
  - this can be set to -1, or any negative number, if you want there to be no limit on the uses of the item
- `increment` is the amount of slots the item gives each time it is used
  - if adding the increment to you current slots would put you over `maxSlots` the mod will just set you to `maxSlots` instead

Items can be used multiple times in the list but mod will combine the instances to determine how the item works. The item will use the smallest `minSlots`, the largest `maxSlots`, and the average of all the `increment`s. This will be the resulting behavior of the item but each bosses will still use the `maxSlots` listed for them as the condition instead of the combined `maxSlots` of the item.

### Adding a drop for a boss
Any mob with a `privateLootTable` can be given a drop to increase trinkets, even bosses from other mods. All you need to do is find the `stringID` for the mob or it's complete class name. 
Finding the `stringID` is much simple, all you need to do is the following:
1. go into a world that has cheats enabled
   - cheats can by entering `/allowcheats` twice in the chat
2. open the cheat menu by pressing F10
3. click mobs in the cheat menu
4. search for the mob in there
   - The mobs in the list are listed under their `stringID` so if you find the mob you are looking for you have also found the name you should use in this mod's settings.
     - for example the `stringID` for Cryo Queen is: `cryoqueen`
     - for example the `stringID` for Ignis the Lord of Flames from the mod [Aspects of Alteration](https://steamcommunity.com/sharedfiles/filedetails/?id=2997987435&searchtext=) is: `aoaboss2mob`
   - the mob that is used to spawn the boss may not always be the mob that actually has the loot table that is used
   - currently I don't know why but neither `sageandgrit` or `flyingspiritsbody` work so the class name has to be used instead
     - `necesse.entity.mobs.hostile.bosses.FlyingSpiritsHead`
   - in some situations like this it may be best to find the full class name and use that instead
5. copy the name from here into the settings file

If you can't find the `stringID` or it is giving you issues you can always find the mob's full class name instead. This process in a bit more complicated so I won't go into detail but the basic steps are:
1. find the mod's or game's jar file
2. decompile it with a Java decompiler application
3. find the mob's class
4. copy the full class name
   - for example the full class name of the mob that contains the loot table for Sage and Grit is:
     - `necesse.entity.mobs.hostile.bosses.FlyingSpiritsHead`
   - for example the full name of Ignis the Lord of Flames from the mod [Aspects of Alteration](https://steamcommunity.com/sharedfiles/filedetails/?id=2997987435&searchtext=) is:
     - `AoA.examples.AoABoss2Mob`
5. paste the full class name into the settings file

Below I will list the `itemID`s from this mod and the base game that can be used. I will also list `stringID`s and full class names for bosses in the base game.

### Mod's `itemIDS`
- Belt Clip
  - `moretrinketslots_evilsprotector`
- Spider Leg Keychain
  - `moretrinketslots_queenspider`
- Empty Pendant
  - `emptypendant`
- Protective Case
  - `moretrinketslots_swampguardian`
- Pocket Protector
  - `moretrinketslots_ancientvulture`
- Belt Bag
  - `moretrinketslots_piratecaptain`
- Trinket Pouch
  - `moretrinketslots_reaper`
- Charm Bracelet
  - `moretrinketslots_cryoqueen`
- Swamp Fruit
  - `moretrinketslots_pestwarden`
- Dragon Satchel
  - `moretrinketslots_sageandgrit`
- Wizard Socket
  - `wizardsocket`
- Dry Bag
  - `moretrinketslots_motherslime`
- Sling Bag
  - `moretrinketslots_nighswarm`
- How To Hold More Trinkets: Vol. 1
  - `moretrinketslots_genericitem_1`
- . . .
- How To Hold More Trinkets: Vol. 9
  - `moretrinketslots_genericitem_9`

### Boss `stringID`s and Full Class Names
- Evil's Protector
  - evilsprotector
  - `necesse.entity.mobs.hostile.bosses.EvilsProtectorMob`
- Queen Spider
  - `queenspider`
  - `necesse.entity.mobs.hostile.bosses.QueenSpiderMob`
- Void Wizard
  - `voidwizard`
  - `necesse.entity.mobs.hostile.bosses.VoidWizard`
- Swamp Guardian
  - `swampguardian`
  - `necesse.entity.mobs.hostile.bosses.SwampGuardianHead`
- Ancient Vulture
  - `ancientvulture`
  - `necesse.entity.mobs.hostile.bosses.AncientVultureMob`
- Pirate Captain
  - `piratecaptain`
  - `necesse.entity.mobs.hostile.bosses.PirateCaptainMob`
- Reaper
  - `reaper`
  - `necesse.entity.mobs.hostile.bosses.ReaperMob`
- Cryo Queen
  - `cryoqueen`
  - `necesse.entity.mobs.hostile.bosses.CryoQueenMob`
- Pest Warden
  - `pestwarden`
  - `necesse.entity.mobs.hostile.bosses.PestWardenHead`
- Sage and Grit
  - `necesse.entity.mobs.hostile.bosses.FlyingSpiritsHead`
- Fallen Wizard
  - `fallenwizard`
  - `necesse.entity.mobs.hostile.bosses.FallenWizardMob`
- Mother Slime
  - `motherslime`
  - `necesse.entity.mobs.hostile.bosses.MotherSlimeMob`
- Night Swarm
  - `nightswarm`
  - `necesse.entity.mobs.hostile.bosses.NightSwarmLevelEvent`

## Download
For client use, the mod can be downloaded through the steam workshop page [here](https://steamcommunity.com/sharedfiles/filedetails/?id=3052859125).<br>
For server use, the jar file jar can be downloaded [here](https://github.com/project-curiosity/MoreTrinketSlots/releases).

## Bugs and Recommendations
Recommendations for how to improve this mod are highly recommended.
You can report bugs, suggest updates, or ask for help on github [here](https://github.com/project-curiosity/MoreTrinketSlots/issues) or on the steam workshop discussion [here](https://steamcommunity.com/workshop/filedetails/discussion/3052859125/3934517363281859481/).

## Future Updates
As of right now I consider this mod to be feature complete. This means that I do not plan on making any large changes or updates to this mod. I will primarily only update the mod to fix bugs and keep it up to date for future game updates. However, I would love to hear everyone's ideas on how I can improve the mod. If I like the idea or if the idea is requested enough I may add it into the mod. You can make suggestions on the github page [here](https://github.com/project-curiosity/MoreTrinketSlots/issues) or on the steam workshop discussion [here](https://steamcommunity.com/workshop/filedetails/discussion/3052859125/3934517363281861155/).
