package com.bravos.news.listener;

import java.util.HashMap;
import java.util.UUID;

public class DataStorage {

    private final static HashMap<UUID,Integer> VIEWS_COUNT_MAP = new HashMap<>();

    public synchronized static HashMap<UUID,Integer> GET_VIEW_COUNT_MAP() {
        return VIEWS_COUNT_MAP;
    }



}
