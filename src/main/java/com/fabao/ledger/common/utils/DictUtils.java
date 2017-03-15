package com.fabao.ledger.common.utils;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fabao.ledger.modules.sys.entity.SysDict;
import com.fabao.ledger.modules.sys.service.SysDictManager;
import com.fabaoframework.modules.springextend.SpringContextHolder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 字典工具类
 * 
 * @author taote
 * @version 2014-8-7
 */
public class DictUtils {

	private static SysDictManager dictManager = SpringContextHolder
			.getBean("sysDictManager");


	public static final String CACHE_DICT_MAP = "dictMap";

	public static String getDictLabel(String value, String type,
			String defaultValue) {
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
			for (SysDict dict : getDictList(type)) {
				if (type.equals(dict.getType())
						&& value.equals(dict.getValue())) {
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type,
			String defaultLabel) {
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
			for (SysDict dict : getDictList(type)) {
				if (type.equals(dict.getType())
						&& label.equals(dict.getLabel())) {
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}

	public static List<SysDict> getDictList(String type) {
		@SuppressWarnings("unchecked")
		Map<String, List<SysDict>> dictMap = (Map<String, List<SysDict>>) CacheUtils
				.get(CACHE_DICT_MAP);
		if (dictMap == null || dictMap.size() == 0) {
			dictMap = Maps.newHashMap();
			SysDict paramDict = new SysDict();
			paramDict.setIsDeleted(false);
			for (SysDict dict : dictManager.getByEntity(paramDict)) {
				List<SysDict> dictList = dictMap.get(dict.getType());
				if (dictList != null) {
					dictList.add(dict);
				} else {
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<SysDict> dictList = dictMap.get(type);
		if (dictList == null) {
			dictList = Lists.newArrayList();
		}
		return dictList;
	}

}