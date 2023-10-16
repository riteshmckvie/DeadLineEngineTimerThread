package com.ritesh.service.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.ritesh.service.DeadlineEngine;

public class DeadlineEngineImpl implements DeadlineEngine {

	Map<Long, Long> hashMap = new ConcurrentHashMap<>();

	@Override
	public long schedule(long deadlineMs) {
		long requestId = ThreadLocalRandom.current().nextLong(1,Long.MAX_VALUE);
		this.hashMap.put(requestId, deadlineMs);
		System.out.println("requestId for "+deadlineMs + " "+requestId);
		return requestId;
	}

	@Override
	public boolean cancel(long requestId) {
		return hashMap.remove(requestId) == null ? false : true;
	}

	@Override
	public int poll(long nowMs, Consumer<Long> handler, int maxPoll) {
		Map<Long, Long> eligibleMap = hashMap.entrySet().stream().filter(item -> item.getValue() >= nowMs)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		int count = 0;

		for (Map.Entry<Long, Long> entry : eligibleMap.entrySet()) {
			if (count >= maxPoll)
				break;
			handler.accept(entry.getKey());
			this.cancel(entry.getKey());
			++count;
		}
		return count;
	}

	@Override
	public int size() {
		return hashMap.size();
	}

}
