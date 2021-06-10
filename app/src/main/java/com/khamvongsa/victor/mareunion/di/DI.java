package com.khamvongsa.victor.mareunion.di;

import com.khamvongsa.victor.mareunion.service.FakeMeetingApiService;
import com.khamvongsa.victor.mareunion.service.MeetingApiService;

/**
 * Dependency injector to get instance of services
 */
public class DI {

        private static final MeetingApiService service = new FakeMeetingApiService();

        /**
         * Créé une instance de @{@link MeetingApiService}
         * @return @{@link MeetingApiService}
         */
        public static MeetingApiService getMeetingApiService() {
            return service;
        }

    /**
     * Créé une instanced de @{@link MeetingApiService}. Utile pour les tests, afin d'être sûr que le contexte est propre.
     * @return @{@link MeetingApiService}
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new FakeMeetingApiService();
    }
}