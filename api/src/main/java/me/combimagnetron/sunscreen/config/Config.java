package me.combimagnetron.sunscreen.config;

@me.combimagnetron.passport.config.annotation.Config
public record Config(double screenDistance, boolean forceShaderFov, boolean screenSetup, String setupImageUrl) {
}
