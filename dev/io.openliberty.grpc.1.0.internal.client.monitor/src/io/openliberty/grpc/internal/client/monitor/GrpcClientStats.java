/*******************************************************************************
 * Copyright (c) 2020, 2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.grpc.internal.client.monitor;

import com.ibm.websphere.monitor.meters.Counter;
import com.ibm.websphere.monitor.meters.Meter;
import com.ibm.websphere.monitor.meters.StatisticsMeter;

import io.openliberty.grpc.internal.client.monitor.GrpcClientStatsMXBean;

/**
 * Holds metrics used for client-side monitoring of gRPC services. </br>
 * Statistic reported:
 * <ul>
 * <li>Total number of RPCs started on the client.
 * <li>Total number of RPCs completed on the client, regardless of success or
 * failure.
 * <li>Histogram of RPC response latency for completed RPCs in milliseconds.
 * <li>Total number of stream messages received from the server.
 * <li>Total number of stream messages sent by the client.
 * </ul>
 */
public class GrpcClientStats extends Meter implements GrpcClientStatsMXBean {
	private final Counter rpcStarted;
	private final Counter rpcCompleted;
	private final Counter streamMessagesReceived;
	private final Counter streamMessagesSent;
    private final StatisticsMeter responseTime;

	private final GrpcMethod method;

	public GrpcClientStats(GrpcMethod method) {
		this.method = method;

		rpcStarted = new Counter();
		rpcStarted.setDescription("This shows total number of RPCs started on the client");
		rpcStarted.setUnit("ns");

		rpcCompleted = new Counter();
		rpcCompleted.setDescription("This shows total number of RPCs completed on the client");
		rpcCompleted.setUnit("ns");

		streamMessagesReceived = new Counter();
		streamMessagesReceived.setDescription("This shows total number of stream messages received from the server");
		streamMessagesReceived.setUnit("ns");

		streamMessagesSent = new Counter();
		streamMessagesSent.setDescription("This shows total number of stream messages sent by the client");
		streamMessagesSent.setUnit("ns");

        responseTime = new StatisticsMeter();
        responseTime.setDescription("Average RPC Response Time");
        responseTime.setUnit("ns");
	}

	@Override
	public long getRpcStartedCount() {
		return rpcStarted.getCurrentValue();
	}

	@Override
	public long getRpcCompletedCount() {
		return rpcCompleted.getCurrentValue();
	}

	@Override
	public long getReceivedMessagesCount() {
		return streamMessagesReceived.getCurrentValue();
	}

	@Override
	public long getSentMessagesCount() {
		return streamMessagesSent.getCurrentValue();
	}

    @Override
    public double getResponseTime() {
        return responseTime.getMean();
    }

    @Override
    public StatisticsMeter getResponseTimeDetails() {
        return responseTime;
    }

    public void recordCallStarted() {
		rpcStarted.incrementBy(1);
	}

	public void recordClientHandled() {
		rpcCompleted.incrementBy(1);
	}

	/**
	 * This will increment received messages count by the specified number.
	 * 
	 * @param i
	 */
	public void incrementReceivedMsgCountBy(int i) {
		this.streamMessagesReceived.incrementBy(i);
	}

	/**
	 * This will increment sent messages count by the specified number.
	 * 
	 * @param i
	 */
	public void incrementSentMsgCountBy(int i) {
		this.streamMessagesSent.incrementBy(i);
	}

	public void recordLatency(long latencyMs) {
		responseTime.addDataPoint(latencyMs);
	}
}
