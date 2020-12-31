package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.entity.MvtTile;
import edu.zju.gis.dldsj.server.service.TileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-11-23
 */
@CrossOrigin
@RestController
@RequestMapping("/tile")
public class TileController {
    private final TileService tileService;

    @Autowired
    public TileController(TileService tileService) {
        this.tileService = tileService;
    }

    @GetMapping("/pg/{tableName}/{layerName}/{geomName}/{z}/{x}/{y}")
    public void get(@PathVariable("tableName") String tableName, @PathVariable("layerName") String layerName,
                    @PathVariable("geomName") String geomName, @PathVariable("z") Integer z,
                    @PathVariable("x") Integer x, @PathVariable("y") Integer y, HttpServletResponse response) {
        MvtTile tile = tileService.getTile(tableName, layerName, geomName, z, x, y);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            IOUtils.write(tile.getImg(), os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
