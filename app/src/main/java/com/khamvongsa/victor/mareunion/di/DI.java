package com.khamvongsa.victor.mareunion.di;

import com.khamvongsa.victor.mareunion.service.FakeReunionApiService;
import com.khamvongsa.victor.mareunion.service.ReunionApiService;

/**
 * Dependency injector to get instance of services
 */
public class DI {

        private static final ReunionApiService service = new FakeReunionApiService();

        /**
         * Get an instance on @{@link ReunionApiService}
         * @return
         */
        public static ReunionApiService getReunionApiService() {
            return service;
        }

    }
