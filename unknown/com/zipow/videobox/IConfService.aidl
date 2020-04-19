package com.zipow.videobox;

interface IConfService {
	void sendMessage(in byte[] message);
	boolean isConfAppAtFront();
	
	void sinkPTAppEvent(int event, long result);
	void sinkDataNetworkStatusChanged(boolean connected);
	void sinkIMLocalStatusChanged(int status);
	void sinkIMReceived(in byte[] content);
	void sinkIMBuddyPresence(in byte[] content);
	void sinkIMBuddyPic(in byte[] content);
	void sinkIMBuddySort();
	void sinkPTAppCustomEvent(int event, long result);
	void onPTUIMoveToFront(String activityClass);
	void onPTUIMoveToBackground();
	void leaveCurrentMeeting(boolean endIfPossible);
	void pauseCurrentMeeting();
	void resumeCurrentMeeting();
	boolean isCurrentMeetingLocked();
	boolean isCurrentMeetingHost();
	boolean startCallOut(String number);
	boolean hangUpCallOut();
	boolean isCallOutInProgress();
	int getCallMeStatus();
	boolean isCallOutSupported();
	boolean isInviteRoomSystemSupported();
	void onRoomCallEvent(int event, long result, boolean isActiveMeeting);
	boolean tryRetrieveMicrophone();
	void onSipCallEvent(int status,String callId);
	void onSipStatusEvent(boolean isAvailable);
}
