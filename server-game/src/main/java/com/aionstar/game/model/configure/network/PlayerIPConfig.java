package com.aionstar.game.model.configure.network;

import com.aionstar.commons.network.model.IPRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.annotation.PostConstruct;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * 玩家允许登录的ip范围设置
 * @author saltman155
 * @date 2020/1/18 20:19
 */

@Component
public class PlayerIPConfig {

    private static final Logger logger = LoggerFactory.getLogger(PlayerIPConfig.class);

    private static final String CONFIG_FILE = "./config/network/player_ip_config.xml";

    private final List<IPRange> ranges = new ArrayList<>();

    private byte[] defaultAddress;

    private static PlayerIPConfig instance;

    @PostConstruct
    private void load(){
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(new File(CONFIG_FILE), new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equals("ipconfig")) {
                        try { defaultAddress = InetAddress.getByName(attributes.getValue("default")).getAddress(); }
                        catch (UnknownHostException e) {
                            throw new RuntimeException("Failed to resolve DSN for address: " + attributes.getValue("default"), e);
                        }
                    }
                    else if (qName.equals("iprange")) {
                        String min = attributes.getValue("min");
                        String max = attributes.getValue("max");
                        String address = attributes.getValue("address");
                        IPRange ipRange = new IPRange(min, max, address);
                        ranges.add(ipRange);
                    }
                }
            });
            instance = this;
            logger.info("用户登录ip配置载入完成...");
        }
        catch (Exception e) {
            logger.error("无法载入 player_ip_config.xml！", e);
            throw new Error("载入 player_ip_config.xml 失败！", e);
        }
    }

    public List<IPRange> getRanges() {
        return ranges;
    }

    public byte[] getDefaultAddress() {
        return defaultAddress;
    }

    public static PlayerIPConfig getInstance(){
        return instance;
    }

}
