package org.ip3e.ddos_guard.Version;

import org.bukkit.Bukkit;

public class VersionChecker {

    public static boolean isVersionAtLeast(String version) {
        String serverVersion = Bukkit.getVersion();
        return serverVersion.contains(version);
    }

    public static boolean isLegacyVersion() {
        return isVersionAtLeast("1.16") && !isVersionAtLeast("1.17");
    }
}