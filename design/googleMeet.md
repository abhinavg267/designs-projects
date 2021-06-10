## Usage 
- User will registers 
- User can create a meeting 
- User can cancel the meeting 
- User can add/remove other users


## Entity 
- User 
    - UserId 
    - name 

- Meeting 
    - MeetingId 
    - startTime 
    - endTime 
    - status 
        - ACTIVE
        - CANCELLED 
    - scheduledBy

- MeetingUserAssociation 
    - MeetingId 
    - UserId 
    - Status 
        - PENDING 
        - ACCEPTED
        - REJECTED 
        - REMOVED

## Services 
- UserManager
    - `addNewUser(name: String): UserId` 
    
- MeetingManager
    - `createNewMeeting(startTime: DateTime, endTime: DateTime, scheduledBy: UserId, paritcipants: Seq[UserId]): Unit`
    - `addNewPariticipants(meetingId: MeetingId, paritcipants: Seq[UserId]): Unit`
    - `removeParticipants(meetingId: MeetingId, paritcipants: Seq[UserId]): Unit`
    - `cancelMeeting(meetingId: MeetingId, userId: UserId): Unit`
    - `acceptOrRejectMeeting(meetingId: MeetingId, userId: UserId, meetingUserAssociationStatus: MeetingUserAssociationStatus): Unit`
    
- UserInfoManager
    - `isUserAvailable(userId: UserId, startTime: DateTime, endTime: DateTime): Boolean`
    - `viewAllScheduledMeeting(userId: UserId, meetingStatues: Option[Set[MeetingStatus]], 
                               meetingUserAssociationStatuses: Option[Set[MeetingUserAssociationStatus]])
        : Map[Status, Seq[Meeting]]` 

