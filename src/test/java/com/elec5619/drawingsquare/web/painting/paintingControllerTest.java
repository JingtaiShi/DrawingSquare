package com.elec5619.drawingsquare.web.painting;

import com.elec5619.drawingsquare.common.utils.ResultVo;
import com.elec5619.drawingsquare.web.painting.controller.PaintingController;
import com.elec5619.drawingsquare.web.painting.entity.Liked;
import com.elec5619.drawingsquare.web.painting.entity.Painting;
import com.elec5619.drawingsquare.web.painting.entity.PaintingParm;
import com.elec5619.drawingsquare.web.painting.entity.Purchased;
import com.elec5619.drawingsquare.web.painting.service.LikedService;
import com.elec5619.drawingsquare.web.painting.service.PaintingService;
import com.elec5619.drawingsquare.web.painting.service.PurchasedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class paintingControllerTest {

    @InjectMocks
    private PaintingController paintingController;

    @Mock
    private PaintingService paintingService;

    @Mock
    private PurchasedService purchasedService;

    @Mock
    private LikedService likedService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetHistoryList() {
        // Mock the list method to return a list of paintings
        List<Painting> paintingList = new ArrayList<>();
        when(paintingService.list(any())).thenReturn(paintingList);

        // Test the getHistoryList method
        ResultVo result = paintingController.getHistoryList(1L);

        assertEquals("query successful", result.getMsg());
        assertEquals(200, result.getCode());
        assertEquals(paintingList, result.getData());
    }

    @Test
    public void testGetLikedPaintingList() {
        // Mock the list method for Liked to return a list of liked paintings
        List<Liked> likedList = new ArrayList<>();
        when(likedService.list(any())).thenReturn(likedList);

        // Mock the getOne method for Painting to return a painting for each liked painting
        Painting painting = new Painting();
        when(paintingService.getOne(any())).thenReturn(painting);

        // Test the getLikedPaintingList method
        ResultVo result = paintingController.getLikedPaintingList(1L);

        assertEquals("query successful", result.getMsg());
        assertEquals(200, result.getCode());
        assertEquals(likedList, result.getData());
    }

    @Test
    public void testGetPurchasedPaintingList() {
        // Mock the list method for Purchased to return a list of purchased paintings
        List<Purchased> purchasedList = new ArrayList<>();
        when(purchasedService.list(any())).thenReturn(purchasedList);

        // Mock the getOne method for Painting to return a painting for each purchased painting
        Painting painting = new Painting();
        when(paintingService.getOne(any())).thenReturn(painting);

        // Test the getPurchasedPaintingList method
        ResultVo result = paintingController.getPurchasedPaintingList(1L);

        assertEquals("query successful", result.getMsg());
        assertEquals(200, result.getCode());
        assertEquals(purchasedList, result.getData());
    }

    @Test
    public void testAddImageToLibrary() {
        // Mock the getOne method for Purchased to return null (indicating the purchase does not exist)
        when(purchasedService.getOne(any())).thenReturn(null);

        // Mock the save method for Purchased to return true (indicating successful purchase)
        when(purchasedService.save(any())).thenReturn(true);

        // Test the addImageToLibrary method
        Purchased purchased = new Purchased();
        ResultVo result = paintingController.addImageToLibrary(purchased);

        assertEquals("Purchase drawing success!", result.getMsg());
        assertEquals(200, result.getCode());
    }

    @Test
    public void testLike() {
        // Mock the getOne method for Liked to return null (indicating the like does not exist)
        when(likedService.getOne(any())).thenReturn(null);

        // Mock the getById method for Painting to return a painting
        Painting painting = new Painting();
        when(paintingService.getById(any())).thenReturn(painting);

        // Mock the save method for Liked to return true (indicating successful like)
        when(likedService.save(any())).thenReturn(true);

        // Mock the saveOrUpdate method for Painting to return true (indicating successful update)
        when(paintingService.saveOrUpdate(any())).thenReturn(true);

        // Test the like method
        Liked liked = new Liked();
        ResultVo result = paintingController.like(liked);

        assertEquals("Picture collection success!", result.getMsg());
        assertEquals(200, result.getCode());
    }

    @Test
    public void testRemoveLike() {
        // Mock the getById method for Painting to return a painting
        Painting painting = new Painting();
        when(paintingService.getById(any())).thenReturn(painting);

        // Mock the remove method for Liked to return true (indicating successful removal)
        when(likedService.remove(any())).thenReturn(true);

        // Mock the saveOrUpdate method for Painting to return true (indicating successful update)
        when(paintingService.saveOrUpdate(any())).thenReturn(true);

        // Test the removeLike method
        Liked liked = new Liked();
        ResultVo result = paintingController.removeLike(liked);

        assertEquals("Remove liked drawing success!", result.getMsg());
        assertEquals(200, result.getCode());
    }

    @Test
    public void testSearchImage() {
        // Mock the list method to return a list of paintings
        List<Painting> paintingList = new ArrayList<>();
        when(paintingService.list(any())).thenReturn(paintingList);

        // Test the searchImage method with a tag
        ResultVo resultWithTag = paintingController.searchImage("landscape");

        // Test the searchImage method without a tag
        ResultVo resultWithoutTag = paintingController.searchImage("");

        assertEquals("query successful", resultWithTag.getMsg());
        assertEquals(200, resultWithTag.getCode());
        assertEquals(paintingList, resultWithTag.getData());

        assertEquals("query successful", resultWithoutTag.getMsg());
        assertEquals(200, resultWithoutTag.getCode());
        assertEquals(paintingList, resultWithoutTag.getData());
    }

    @Test
    public void testGetList() {
        // Mock the page method to return a list of paintings
        List<Painting> paintingList = new ArrayList<>();
        when(paintingService.page(any(), any())).thenReturn(paintingList);

        // Test the getList method
        PaintingParm paintingParm = new PaintingParm();
        ResultVo result = paintingController.getList(paintingParm);

        assertEquals("query successful", result.getMsg());
        assertEquals(200, result.getCode());
        assertEquals(paintingList, result.getData());
    }

    @Test
    public void testGetGallery() {
        // Mock the list method to return a list of paintings
        List<Painting> paintingList = new ArrayList<>();
        when(paintingService.list(any())).thenReturn(paintingList);

        // Test the getGallery method
        ResultVo result = paintingController.getList();

        assertEquals("query successful", result.getMsg());
        assertEquals(200, result.getCode());
        assertEquals(paintingList, result.getData());
    }


    @Test
    public void testAddImgApi() {
        // Mock the getById method for Painting to return null (indicating the painting does not exist)
        when(paintingService.getById(any())).thenReturn(null);

        // Mock the save method for Painting to return true (indicating successful addition)
        when(paintingService.save(any())).thenReturn(true);

        // Test the addImgApi method for adding a new painting
        Painting painting = new Painting();
        ResultVo resultAddNew = paintingController.add(painting);

        // Test the addImgApi method for editing an existing painting
        when(paintingService.updateById(any())).thenReturn(true);
        ResultVo resultEditExisting = paintingController.add(painting);

        assertEquals("add drawing successful!", resultAddNew.getMsg());
        assertEquals(200, resultAddNew.getCode());

        assertEquals("edit drawing successful", resultEditExisting.getMsg());
        assertEquals(200, resultEditExisting.getCode());
    }

    @Test
    public void testEdit() {
        // Mock the updateById method for Painting to return true (indicating successful update)
        when(paintingService.updateById(any())).thenReturn(true);

        // Test the edit method
        Painting painting = new Painting();
        ResultVo result = paintingController.edit(painting);

        assertEquals("edit drawings successful!", result.getMsg());
        assertEquals(200, result.getCode());
    }

    @Test
    public void testDelete() {
        // Mock the removeById method for Painting to return true (indicating successful deletion)
        when(paintingService.removeById(anyInt())).thenReturn(true);

        // Test the delete method
        ResultVo result = paintingController.delete(1);

        assertEquals("delete drawing successful!", result.getMsg());
        assertEquals(200, result.getCode());
    }

}
