package com.khamvongsa.victor.mareunion.di;

import com.khamvongsa.victor.mareunion.service.FakeMeetingApiService;
import com.khamvongsa.victor.mareunion.service.MeetingApiService;

/**
 * Dependency injector to get instance of services
 */
public class DI {

        private static final MeetingApiService service = new FakeMeetingApiService();

        /**
         * Get an instance on @{@link MeetingApiService}
         * @return
         */
        public static MeetingApiService getReunionApiService() {
            return service;
        }

    /**
     * Get always a new instance on @{@link MeetingApiService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new FakeMeetingApiService();
    }
}
