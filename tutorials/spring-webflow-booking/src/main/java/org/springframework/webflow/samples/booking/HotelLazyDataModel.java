/*
 * Copyright 2004-2013 ICEsoft Technologies Canada Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

/**
 *
 */
package org.springframework.webflow.samples.booking;

import java.util.List;
import java.util.Map;


public class HotelLazyDataModel extends LazyDataModel<Hotel> {

    private static final long serialVersionUID = -8832831134966938627L;

    SearchCriteria searchCriteria;

    BookingService bookingService;

    private Hotel selected;

    public HotelLazyDataModel(SearchCriteria searchCriteria, BookingService bookingService) {
        this.searchCriteria = searchCriteria;
        this.bookingService = bookingService;
    }

    @Override
    public List<Hotel> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {
        searchCriteria.setCurrentPage(first / pageSize + 1);
        List<Hotel> hotels = bookingService.findHotels(searchCriteria, first, sortField, sortOrder);
        return hotels;
    }

    @Override
    public int getRowCount() {
        return bookingService.getNumberOfHotels(searchCriteria);
    }

    public Hotel getSelected() {
        return selected;
    }

    public void setSelected(Hotel selected) {
        this.selected = selected;
    }

}