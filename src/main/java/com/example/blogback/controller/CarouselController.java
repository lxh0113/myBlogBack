package com.example.blogback.controller;

import com.example.blogback.common.R;
import com.example.blogback.dao.CarouselDao;
import com.example.blogback.domain.Carousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/carousel")
public class CarouselController {

    @Autowired
    private CarouselDao carouselDao;

    @GetMapping
    public R getCarousels(){
        List<Carousel> carousels = carouselDao.selectList(null);
        return R.success(carousels);
    }

    @PostMapping
    public R changeCarousel(@RequestBody Integer id,@RequestBody String url){
        int i = carouselDao.updateById(new Carousel(id, url));
        if(i>0) return R.success("成功");
        else return R.error("失败");
    }

    @PutMapping
    public R addCarousel(@RequestParam String url){
        System.out.println(url);
        int insert = carouselDao.insert(new Carousel(null, url));
        if(insert>0) return R.success("成功");
        else return R.error("失败");
    }

    @DeleteMapping("/{id}")
    public R deleteCarousel(@PathVariable Integer id){
        int i = carouselDao.deleteById(id);
        if(i>0) return R.success("成功");
        else return R.error("失败");
    }

}
