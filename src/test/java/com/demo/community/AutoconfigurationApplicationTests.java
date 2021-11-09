package com.demo.community;

import com.demo.community.pojo.Fruit;
import com.demo.community.property.FruitProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AutoconfigurationApplicationTests {

    @Autowired
    private FruitProperty fruitProperty;
    
    @Value("${property.test.name}")
    private String propertyTestName;

    @Value("${propertyTest}")
    private String propertyTest;

    @Value("${noKey:default value}")
    private String defaultValue;

    @Value("${propertyTestList}")
    private String[] propertyTestArray;

    @Value("#{'${propertyTestList}'.split(',')}")
    private List<String> propertyTestList;

    @Test
    public void testValue() {
        assertThat(propertyTestName).isEqualTo("property depth test");
        assertThat(propertyTest).isEqualTo("test");
        assertThat(defaultValue).isEqualTo("default value");

        assertThat(propertyTestArray[0]).isEqualTo("a");
        assertThat(propertyTestArray[1]).isEqualTo("b");
        assertThat(propertyTestArray[2]).isEqualTo("c");

        assertThat(propertyTestList.get(0)).isEqualTo("a");
        assertThat(propertyTestList.get(1)).isEqualTo("b");
        assertThat(propertyTestList.get(2)).isEqualTo("c");

    }

//    @Test
//    public void configurationPropertiesTest() {
//        List<Map> fruitData = fruitProperty.getList();
//
//        assertThat(fruitData.get(0).get("name")).isEqualTo("banana");
//        assertThat(fruitData.get(0).get("color")).isEqualTo("yellow");
//        assertThat(fruitData.get(1).get("name")).isEqualTo("apple");
//        assertThat(fruitData.get(1).get("color")).isEqualTo("red");
//        assertThat(fruitData.get(2).get("name")).isEqualTo("water melon");
//        assertThat(fruitData.get(2).get("color")).isEqualTo("green");
//    }

    @Test
    public void configurationPropertiesTestPojo() {
        List<Fruit> fruitData = fruitProperty.getList();

        assertThat(fruitData.get(0).getName()).isEqualTo("banana");
        assertThat(fruitData.get(0).getColor()).isEqualTo("yellow");
        assertThat(fruitData.get(1).getName()).isEqualTo("apple");
        assertThat(fruitData.get(1).getColor()).isEqualTo("red");
        assertThat(fruitData.get(2).getName()).isEqualTo("water melon");
        assertThat(fruitData.get(2).getColor()).isEqualTo("green");
    }
}
