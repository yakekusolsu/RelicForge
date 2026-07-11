# RelicForge

Paper 1.21.8 / Java 21 向けの RPG 装備拡張プラグインです。武器・防具にオーブスロットを付与し、64 種類のオーブを装着して追加効果を得られます。チャーム機能は基礎実装として、7 スロット GUI、PDC 付きチャームアイテム、図鑑表示、装備制限を備えています。

アイテム説明はハックアンドスラッシュ系の装備拡張を意識し、以下の3分類で表示します。

- 基本ステータス（基礎スペック）: 種別、レアリティ、属性、装備枠など
- オプション効果（特性・エンチャント）: Affix、Prefix、Corruption、発動条件など

## 対応環境

- Java 21
- Paper API 1.21.8
- Gradle
- Vault は任意です。Vault または Economy provider が無い場合、売却機能だけ無効化され、プラグイン本体は起動します。

NMS は使っていません。アイテムデータは PersistentDataContainer に保存します。

## ビルド

```bash
gradle build
```

生成物は `build/libs/CharmOrbPlugin-1.0.4.jar` です。

## インストール

1. 生成された jar を Paper サーバーの `plugins` フォルダに入れます。
2. サーバーを起動します。
3. `plugins/CharmOrbPlugin/config.yml` が生成されます。
4. 必要に応じて確率、価格、倍率、クールタイムを編集します。
5. `/orb reload` で設定を再読込します。

## 権限

- `charmorb.use`: 通常利用。デフォルト true
- `charmorb.admin`: 管理系コマンド。デフォルト op

## /orb コマンド

- `/orb help`: ヘルプを表示
- `/orb list`: 全オーブ ID をレアリティごとに表示
- `/orb give <id>`: 指定 ID のオーブを付与
- `/orb give-random`: ランダムなオーブを付与
- `/orb give-rarity <rarity>`: 指定レアリティからランダム付与
- `/orb give-all`: 全 64 種類のオーブを付与
- `/orb roll`: メインハンドの対象装備にランダムスロットを付与
- `/orb setslots <0-5>`: メインハンド装備のスロット数を設定
- `/orb check`: スロット、装着オーブ、魂喰い成長値、PDC 情報を表示
- `/orb insert <slot> <id>`: 管理者用の直接装着
- `/orb remove <slot>`: 指定スロットのオーブを解除して返却
- `/orb gui`: オーブ装着 GUI を開く
- `/orb effects`: メインハンド装備の効果一覧を表示
- `/orb sell`: 買取 GUI を開く
- `/orb reload`: `config.yml` と `messages.yml` を再読込
- `/sellorb`: 買取 GUI を開く

## /charm コマンド

- `/charm help`: ヘルプを表示
- `/charm slot`: 7 スロットのチャーム GUI を開く
- `/charm give <element_rarity>`: チャームを付与。例: `fire_common`
- `/charm list`: 属性カテゴリからチャーム一覧 GUI を表示
- `/charm dex`: チャーム図鑑 GUI
- `/charm sell`: 売却 GUI

## オーブ一覧

Common: `vitality`, `mining`, `lightweight`, `guardian`, `power`, `durability`, `satiety`, `swimming`

Uncommon: `gale`, `life_steal`, `fortune`, `precision`, `forestman`, `deep_sea`, `torchlight`, `counter`

Rare: `scorching`, `freezing`, `thunderclap`, `crushing`, `regeneration`, `mana`, `heavy_strike`, `prospecting`

Epic: `thunder_god`, `unyielding`, `blood_rage`, `divine_guardian`, `harvest`, `swift_step`, `storm`, `venom`

Legendary: `phoenix`, `void`, `holy_light`, `space_time`, `awakening`, `giant`, `hunt_god`, `king`

Mythic: `godspeed`, `end`, `star_core`, `world_tree`, `abyss`, `sky`, `dragon_soul`, `creation`

Unique: `fate`, `ruler`, `time`, `infinity`, `chaos`, `hero`, `crown`, `genesis`

Curse: `berserker`, `cursed_blood`, `runaway`, `sacrifice`, `frailty`, `binding`, `deep_abyss`, `soul_eater`

旧 ID `common_growth` は `soul_eater` として読み替えます。

## config.yml

`orb.max-slots` は最大スロット数です。現在の実装上の上限は 5 です。

`orb.slot-roll` で `/orb roll` とクラフト時付与の重みを調整できます。

`orb.soul-eater` では魂喰いの最大レベル、減衰開始秒、減衰間隔、1 レベルあたりの攻撃倍率を調整できます。

`orb.sell-prices` はレアリティごとの売却価格です。

`orb.effects` では主要効果の倍率、確率、クールタイムを調整できます。

`charm` ではチャーム最大スロット数と高レア装備制限を調整できます。現時点の GUI は 7 スロット基準です。

`drops.mob` では通常モブからのオーブ/チャームドロップ率を設定できます。`drops.mob.entities.zombie` のように EntityType 名を小文字で追加すると個別設定できます。

`drops.mob.mythicmobs` は MythicMobs がある場合だけ使われます。`mobs` 配下に MythicMobs の internal name を指定すると、ボスやカスタムモブごとのドロップ率を設定できます。MythicMobs が無い場合、この処理は無視されます。

`drops.ore` では鉱石破壊時のオーブ/チャームドロップ率を設定できます。

## 注意事項

- GUI 操作中にメインハンド装備を変更した場合、処理は中断されます。
- CraftItemEvent によるスロット付与は通常クラフト優先です。Shift クリック大量クラフトや smithing/anvil は TODO として安全側に残しています。
- `space_time`, `star_core`, `fate`, `chaos` など一部の特殊効果は安全な簡易効果または TODO として残しています。
- Vault が無い場合、`/orb sell`, `/sellorb`, `/charm sell` は警告を出して終了します。
- チャームスロット GUI は、下のプレイヤーインベントリ内のチャームをクリックすると空きスロットに装備できます。空きスロットをカーソル上のチャームでクリックしても装備できます。
