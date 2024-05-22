package net.serble.estools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Updater {
    private static void checkForUpdateBlocking() {
        try {
            URL url = new URL(Objects.requireNonNull(Main.plugin.getConfig().getString("updater.github-release-url")));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            if (con.getResponseCode() != 200) {
                Bukkit.getLogger().severe("[EsTools Updater] GitHub gave unsuccessful response code: " + con.getResponseCode());
                return;
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            JsonObject response = gson.fromJson(content.toString(), JsonObject.class);

            String downloadUrl = response.get("assets")
                    .getAsJsonArray()
                    .get(0)
                    .getAsJsonObject()
                    .get("browser_download_url")
                    .getAsString();

            PluginVersion onlineVersion = new PluginVersion(response.get("tag_name").getAsString());
            String cVersion = Main.plugin.getDescription().getVersion();

            if (cVersion.contains("-")) {
                cVersion = cVersion.substring(0, cVersion.indexOf("-"));
            }

            PluginVersion currentVersion = new PluginVersion(cVersion);

            if (!currentVersion.isLowerThan(onlineVersion)) {
                Bukkit.getLogger().info("[EsTools Updater] Plugin is up to date");
                return;
            } else if (Main.plugin.getConfig().getBoolean("updater.warn-on-outdated", false)) {
                Bukkit.broadcast(EsToolsCommand.translate("&a[EsTools] &cAn update is available, &6" + cVersion + " -> " + onlineVersion.getString()), "estools.update");
            }

            if (Main.plugin.getConfig().getBoolean("updater.log-on-outdated", false)) {
                Bukkit.getLogger().info(EsToolsCommand.translate("&a[EsTools] &cAn update is available, &6" + cVersion + " -> " + onlineVersion.getString()));
            }

            if (!Main.plugin.getConfig().getBoolean("updater.auto-update", false)) {
                return;
            }

            downloadFile(downloadUrl);
            Bukkit.getLogger().info("[EsTools Updater] Downloaded latest plugin version");
        } catch (IOException e) {
            Bukkit.getLogger().severe("[EsTools Updater] Failed to check for updates, invalid releases URL configured");
        }
    }

    public static void checkForUpdate() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, Updater::checkForUpdateBlocking);
    }

    public static void downloadFile(String fileURL)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();


        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = Main.plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
            Bukkit.getLogger().info("Save file path: " + saveFilePath);

            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
        } else {
            throw new IOException("Failed to download file, status code: " + responseCode);
        }
        httpConn.disconnect();
    }

}
