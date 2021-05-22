package com.khamvongsa.victor.mareunion;

import com.khamvongsa.victor.mareunion.controller.ExampleMeeting;
import com.khamvongsa.victor.mareunion.controller.ExampleRoom;
import com.khamvongsa.victor.mareunion.di.DI;
import com.khamvongsa.victor.mareunion.service.FakeMeeting;
import com.khamvongsa.victor.mareunion.service.FakeRoom;
import com.khamvongsa.victor.mareunion.service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.graphics.Color.RED;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingApiServiceTest {

    private MeetingApiService service;
    static private final ExampleRoom mMario = new ExampleRoom(0, "Mario", RED);
    static private final List<String> mParticipants = Arrays.asList("Jean", "Baptiste");
    private static final int DATE_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    private static final int START_HOUR = 14;
    private static final int START_MINUTE = 20;
    private static final int END_HOUR = 15;
    private static final int END_MINUTE = 40;


    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<ExampleMeeting> meetings = service.getMeetings();
        List<ExampleMeeting> expectedMeetings = FakeMeeting.EXEMPLE_REUNIONS;
        org.hamcrest.MatcherAssert.assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void getRoomsWithSuccess(){
        List<ExampleRoom> rooms = service.getRooms();
        List<ExampleRoom> expectedRooms  = FakeRoom.SALLES_DISPONIBLES;
        org.hamcrest.MatcherAssert.assertThat(rooms, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedRooms.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        ExampleMeeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void addMeetingWithSuccess() {
        ExampleMeeting meetingAdded = new ExampleMeeting(4, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), mMario, "Test", mParticipants);
        service.createMeeting(meetingAdded);
        assertTrue(service.getMeetings().contains(meetingAdded));
    }

    @Test
    public void getMeetingsByRoomWithSuccess(){
        String mMario = "Mario";
        List<ExampleMeeting> mListByRoom = service.getMeetingsByRooms(mMario);
        ExampleMeeting meetingRoomLuigi = service.getMeetings().get(1);
        // Must return false because this meeting has the wrong roomName
        assertFalse(mListByRoom.contains(meetingRoomLuigi));
        // mListRoom contains only meetings with room "Mario"
        assertTrue(mListByRoom.contains(service.getMeetings().get(0)));
    }

    @Test
    public void getMeetingByDateWithSuccess(){
        ExampleMeeting meetingDateTest = service.getMeetings().get(3);
        ExampleMeeting meetingFalseDateTest = service.getMeetings().get(1);
        Calendar instance = Calendar.getInstance();
        instance.setTime(meetingDateTest.getDebut());
        List<ExampleMeeting> mListByDate = service.getMeetingsByDate(instance);
        // Must return false because this meeting has the wrong date
        assertFalse(mListByDate.contains(meetingFalseDateTest));
        // mListRoom contains only meetings with date "2022/09/05"
        assertTrue(mListByDate.contains(meetingDateTest));
    }

    // Même heure de départ et de fin, mais la réunion choisie commence en même temps ou pendant l'heure d'une réunion déjà présente
    /** TEST avec la 4ème réunion de la liste. @{@link FakeMeeting}*/
    @Test
    public void filterAvailableRoomsWithSuccessOne(){
        List<ExampleMeeting> mMeetingList = service.getMeetings();
        List<ExampleRoom> mRooms = service.getRooms();
        Calendar mDateTest = Calendar.getInstance();
        Calendar mStartTimeTest = Calendar.getInstance();
        Calendar mEndTimeTest = Calendar.getInstance();

        mDateTest.set(DATE_YEAR+2,8,5);
        mStartTimeTest.set(0,0,0,START_HOUR-4,START_MINUTE+10);
        mEndTimeTest.set(0,0,0,END_HOUR-5,END_MINUTE);

        List<String> listRoomsAvailable = service.filterAvailableRooms(mMeetingList,mRooms, mDateTest, mStartTimeTest, mEndTimeTest);
        // "Peach" ne fera pas partie des salles disponibles
        assertFalse(listRoomsAvailable.contains(service.getMeetings().get(3).getRoom().getNom()));
        assertFalse(listRoomsAvailable.contains(service.getMeetings().get(4).getRoom().getNom()));
        // "Mario" et "Luigi" feront partie des salles disponibles
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(0).getRoom().getNom()));
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(1).getRoom().getNom()));
    }

    // Même heure de départ et de fin, mais la réunion choisie commence avant, mais déborde sur la réunion déjà présente.
    /** TEST avec la 4ème réunion de la liste. @{@link FakeMeeting}*/
    @Test
    public void filterAvailableRoomsWithSuccessTwo() {
        List<ExampleMeeting> mMeetingList = service.getMeetings();
        List<ExampleRoom> mRooms = service.getRooms();
        Calendar mDateTest = Calendar.getInstance();
        Calendar mStartTimeTest = Calendar.getInstance();
        Calendar mEndTimeTest = Calendar.getInstance();

        mDateTest.set(DATE_YEAR + 2, 8, 5);
        mStartTimeTest.set(0,0,0,START_HOUR-4,START_MINUTE-10);
        mEndTimeTest.set(0,0,0,END_HOUR-5,END_MINUTE);

        List<String> listRoomsAvailable = service.filterAvailableRooms(mMeetingList, mRooms, mDateTest, mStartTimeTest, mEndTimeTest);
        // "Peach" ne fera pas partie des salles disponibles
        assertFalse(listRoomsAvailable.contains(service.getMeetings().get(4).getRoom().getNom()));
        assertFalse(listRoomsAvailable.contains(service.getMeetings().get(3).getRoom().getNom()));
        // "Mario" et "Luigi" feront partie des salles disponibles
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(0).getRoom().getNom()));
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(1).getRoom().getNom()));
    }

    // Si la réunion choisie commence avant mais qu'elle déborde sur la réunion déjà présente
    /** TEST avec la 3ème réunion de la liste. @{@link FakeMeeting}*/
    @Test
    public void filterAvailableRoomsWithSuccessThree() {
        List<ExampleMeeting> mMeetingList = service.getMeetings();
        List<ExampleRoom> mRooms = service.getRooms();
        Calendar mDateTest = Calendar.getInstance();
        Calendar mStartTimeTest = Calendar.getInstance();
        Calendar mEndTimeTest = Calendar.getInstance();

        mDateTest.set(DATE_YEAR + 1, 8, 5);
        mStartTimeTest.set(0, 0, 0, START_HOUR-1, START_MINUTE);
        mEndTimeTest.set(0, 0, 0, END_HOUR, END_MINUTE);

        List<String> listRoomsAvailable = service.filterAvailableRooms(mMeetingList, mRooms, mDateTest, mStartTimeTest, mEndTimeTest);
        // "Peach" ne fera pas partie des salles disponibles
        assertFalse(listRoomsAvailable.contains(service.getMeetings().get(3).getRoom().getNom()));
        // "Mario" et "Luigi" feront partie des salles disponibles
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(0).getRoom().getNom()));
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(1).getRoom().getNom()));
    }

    // Si la réunion choisie commence pendant une réunion déjà présente à la même heure de départ
    /** TEST avec la 3ème réunion de la liste. @{@link FakeMeeting}*/
    @Test
    public void filterAvailableRoomsWithSuccessFour() {
        List<ExampleMeeting> mMeetingList = service.getMeetings();
        List<ExampleRoom> mRooms = service.getRooms();
        Calendar mDateTest = Calendar.getInstance();
        Calendar mStartTimeTest = Calendar.getInstance();
        Calendar mEndTimeTest = Calendar.getInstance();

        mDateTest.set(DATE_YEAR + 1, 8, 5);
        mStartTimeTest.set(0, 0, 0, START_HOUR, START_MINUTE+10);
        mEndTimeTest.set(0, 0, 0, END_HOUR, END_MINUTE);

        List<String> listRoomsAvailable = service.filterAvailableRooms(mMeetingList, mRooms, mDateTest, mStartTimeTest, mEndTimeTest);
        // "Peach" ne fera pas partie des salles disponibles
        assertFalse(listRoomsAvailable.contains(service.getMeetings().get(3).getRoom().getNom()));
        // "Mario" et "Luigi" feront partie des salles disponibles
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(0).getRoom().getNom()));
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(1).getRoom().getNom()));
    }

    // Si la réunion commence pendant une réunion déjà présente à une heure de départ différente
    /** TEST avec la 3ème réunion de la liste. @{@link FakeMeeting}*/
    @Test
    public void filterAvailableRoomsWithSuccessFive() {
        List<ExampleMeeting> mMeetingList = service.getMeetings();
        List<ExampleRoom> mRooms = service.getRooms();
        Calendar mDateTest = Calendar.getInstance();
        Calendar mStartTimeTest = Calendar.getInstance();
        Calendar mEndTimeTest = Calendar.getInstance();

        mDateTest.set(DATE_YEAR + 1, 8, 5);
        mStartTimeTest.set(0, 0, 0, START_HOUR+1, START_MINUTE);
        mEndTimeTest.set(0, 0, 0, END_HOUR, END_MINUTE);

        List<String> listRoomsAvailable = service.filterAvailableRooms(mMeetingList, mRooms, mDateTest, mStartTimeTest, mEndTimeTest);
        // "Peach" ne fera pas partie des salles disponibles
        assertFalse(listRoomsAvailable.contains(service.getMeetings().get(3).getRoom().getNom()));
        // "Mario" et "Luigi" feront partie des salles disponibles
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(0).getRoom().getNom()));
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(1).getRoom().getNom()));
    }

    // Si la réunion choisie commence avant une réunion déjà présente et finit après
    /** TEST avec la 3ème réunion de la liste. @{@link FakeMeeting}*/
    @Test
    public void filterAvailableRoomsWithSuccessSix() {
        List<ExampleMeeting> mMeetingList = service.getMeetings();
        List<ExampleRoom> mRooms = service.getRooms();
        Calendar mDateTest = Calendar.getInstance();
        Calendar mStartTimeTest = Calendar.getInstance();
        Calendar mEndTimeTest = Calendar.getInstance();

        mDateTest.set(DATE_YEAR + 1, 8, 5);
        mStartTimeTest.set(0, 0, 0, START_HOUR-1, START_MINUTE);
        mEndTimeTest.set(0, 0, 0, END_HOUR+1, END_MINUTE);

        List<String> listRoomsAvailable = service.filterAvailableRooms(mMeetingList, mRooms, mDateTest, mStartTimeTest, mEndTimeTest);
        // "Peach" ne fera pas partie des salles disponibles
        assertFalse(listRoomsAvailable.contains(service.getMeetings().get(3).getRoom().getNom()));
        // "Mario" et "Luigi" feront partie des salles disponibles
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(0).getRoom().getNom()));
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(1).getRoom().getNom()));
    }

    // Si la réunion choisie commence avant une réunion déjà présente et finit à la même heure que l'heure de départ(dela réunion déjà présente), mais quelques minutes plus tard
    /** TEST avec la 3ème réunion de la liste. @{@link FakeMeeting}*/
    @Test
    public void filterAvailableRoomsWithSuccessSeven() {
        List<ExampleMeeting> mMeetingList = service.getMeetings();
        List<ExampleRoom> mRooms = service.getRooms();
        Calendar mDateTest = Calendar.getInstance();
        Calendar mStartTimeTest = Calendar.getInstance();
        Calendar mEndTimeTest = Calendar.getInstance();

        mDateTest.set(DATE_YEAR + 1, 8, 5);
        mStartTimeTest.set(0, 0, 0, START_HOUR-1, START_MINUTE);
        mEndTimeTest.set(0, 0, 0, END_HOUR-1, END_MINUTE+10);

        List<String> listRoomsAvailable = service.filterAvailableRooms(mMeetingList, mRooms, mDateTest, mStartTimeTest, mEndTimeTest);
        // "Peach" ne fera pas partie des salles disponibles
        assertFalse(listRoomsAvailable.contains(service.getMeetings().get(3).getRoom().getNom()));
        // "Mario" et "Luigi" feront partie des salles disponibles
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(0).getRoom().getNom()));
        assertTrue(listRoomsAvailable.contains(service.getMeetings().get(1).getRoom().getNom()));
    }
}
