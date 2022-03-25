import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.Mockito;


class RestaurantTest {
    @Mock
    Restaurant restaurant;
    Restaurant spiedRestaurant;

    List<String> selectedItems = new ArrayList<String>();

    @BeforeEach
    public void initializingData() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        spiedRestaurant = Mockito.spy(restaurant);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime now = LocalTime.parse("15:00:00");
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(now);
        assertEquals(true, restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime now = LocalTime.parse("08:00:00");
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(now);
        assertEquals(true, restaurant.isRestaurantOpen());

    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<TOTALCOST>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void calculate_order_value_should_return_0_for_no_selected_dish() {
        int total = restaurant.calculateOrderValue(selectedItems);
        assertEquals(0, total);
    }

    @Test
    public void calculate_order_value_should_return_the_same_amount_for_one_selected_dish() {
        selectedItems.add("Sizzling brownie");
        int total = restaurant.calculateOrderValue(selectedItems);
        assertEquals(319, total);
    }

    @Test
    public void calculate_order_value_should_return_for_multiple_selected_dish() {

        selectedItems.add("Sizzling brownie");
        selectedItems.add("Sweet corn soup");
        int total = restaurant.calculateOrderValue(selectedItems);
        assertEquals(438, total);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<TOTALCOST>>>>>>>>>>>>>>>>>>>>>>>>>

}