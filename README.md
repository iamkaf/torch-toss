# Torch Toss

[![Amber](https://img.shields.io/badge/Amber-iamkaf?style=for-the-badge&label=Requires&color=%23ebb134)](https://modrinth.com/mod/amber)
[![Issues](https://img.shields.io/github/issues/iamkaf/mod-issues?style=for-the-badge&color=%23eee)](https://github.com/iamkaf/mod-issues)
[![Discord](https://img.shields.io/discord/1207469438719492176?style=for-the-badge&logo=discord&label=DISCORD&color=%235865F2)](https://discord.gg/HV5WgTksaB)
[![KoFi](https://img.shields.io/badge/KoFi-iamkaf?style=for-the-badge&logo=kofi&logoColor=%2330d1e3&label=Support%20Me&color=%2330d1e3)](https://ko-fi.com/iamkaffe)

With Torch Toss, you can throw torches ahead of you, lighting up caves, tunnels, and dark paths with ease!

Requires [Amber](https://modrinth.com/mod/amber) and [Konfig](https://modrinth.com/mod/konfig).

## Development

Torch Toss is maintained as a multiversion, multiloader mod. Shared gameplay code and stable assets live in the top-level `common`, `fabric`, `forge`, and `neoforge` roots. Minecraft-version-specific resources live under `versions/<minecraft>/...`.

Generated runtime resources are committed as source artifacts under:

```text
versions/<minecraft>/common/src/main/generated
```

Run `just datagen-all` after changing datagen providers. Minecraft `1.17` and newer use Fabric datagen to write into the selected version lane. Minecraft `1.14.4` through `1.16.5` use checked-in compatibility lanes because Fabric datagen is not available there as a stable runtime path.

Use `just run <minecraft-version> build` for a full build of one Minecraft line, or `just scenario-check <minecraft-version>-<loader>` for a runtime feature check.
