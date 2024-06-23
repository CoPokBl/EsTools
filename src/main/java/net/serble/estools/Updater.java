package net.serble.estools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.serble.estools.ServerApi.Interfaces.EsCommandSender;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Updater {
    private static String waitingDownloadUrl = null;

    private static void checkForUpdateBlocking() {
        try {
            URL url = new URL(Objects.requireNonNull(Main.plugin.getConfig().getUpdater().getGithubReleasesUrl()));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            if (con.getResponseCode() != 200) {
                Main.logger.severe("[EsTools Updater] GitHub gave unsuccessful response code: " + con.getResponseCode());
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

            SemanticVersion onlineVersion = new SemanticVersion(response.get("tag_name").getAsString());
            SemanticVersion currentVersion = Main.server.getPluginVersion();

            // Provide download URL just in case we force update
            waitingDownloadUrl = downloadUrl;

            if (!currentVersion.isLowerThan(onlineVersion)) {
                return;
            }

            // An update is available
            Main.newVersion = onlineVersion;

            // Announcements
            if (Main.plugin.getConfig().getUpdater().isWarnOnOutdated()) {
                Main.server.broadcast(EsToolsCommand.translate("&a[EsTools] &cAn update is available, &6" + currentVersion + " -> " + onlineVersion), "estools.update");
            }
            if (Main.plugin.getConfig().getUpdater().isLogOnOutdated()) {
                Main.logger.info(EsToolsCommand.translate("&a[EsTools] &cAn update is available, &6" + currentVersion + " -> " + onlineVersion));
            }

            if (!Main.plugin.getConfig().getUpdater().isAutoUpdate()) {
                return;
            }

            downloadNewUpdate();
            Main.logger.info("[EsTools Updater] Downloaded latest plugin version");
            Main.newVersionReady = true;
        } catch (IOException e) {
            Main.logger.severe("[EsTools Updater] Failed to check for updates, invalid releases URL configured");
        }
    }

    public static void downloadNewUpdate() {
        downloadNewUpdate(null);
    }

    public static void downloadNewUpdate(EsCommandSender reportTo) {
        if (waitingDownloadUrl == null) {
            throw new RuntimeException("We aren't waiting for an update");
        }

        try {
            downloadFile(waitingDownloadUrl);
            Main.newVersionReady = true;
        } catch (IOException e) {
            Main.logger.severe("Failed to update: " + e);
            return;
        }

        if (reportTo != null) {
            EsToolsCommand.send(reportTo, "&aThe latest version has been downloaded! Restart the server to apply it.");
        }
    }

    public static void checkForUpdate() {
        //Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, Updater::checkForUpdateBlocking);
        checkForUpdateBlocking();  // It only runs once when the plugin starts, it doesn't need to be async
        // And also Bukkit.getScheduler().runTaskAsynchronously doesn't exist in 1.0 so imma just run it normally
    }

    public static void downloadFile(String fileURL)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();


        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = Main.plugin.getClass().getProtectionDomain()
                    .getCodeSource().getLocation().getFile().replace("%20", " ");
            Main.logger.info("Save file path: " + saveFilePath);

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
