package com.iamkaf.torchtoss;

//? if <=1.16.5 {
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//?} else if >=1.21.11 {
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//?} else {
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//?}

public class TorchTossConstants {
    /**
     * Mod identifier and configuration fields.
     * Update these fields when reusing this code for other projects.
     */
    public static final String MOD_ID = "torchtoss";
    public static final String MOD_NAME = "Torch Toss";
    //? if <=1.16.5 {
    public static final Logger LOG = LogManager.getLogger(MOD_ID);
    //?} else {
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    //?}

    /**
     * Creates a resource location in the mod namespace with the given path.
     */
    //? if >=1.21.11 {
    public static Identifier resource(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    //?} else if >=1.21 {
    public static ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    //?} else {
    public static ResourceLocation resource(String path) {
        return new ResourceLocation(MOD_ID, path);
    //?}
    }
}
