package com.github.gn5r.dynamic.excel.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.gn5r.dynamic.excel.dto.RegulationDto;
import com.github.gn5r.dynamic.excel.dto.RegulationFileDto;
import com.github.gn5r.dynamic.excel.dto.RegulationListDto;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * オブジェクトのリストを操作するサービスクラス
 */
@Service
@Slf4j
public class ListEditService {

    @Autowired
    private ModelMapper modelMapper;
    
    private List<RegulationDto> createDummyData() {
        List<RegulationDto> list = new ArrayList<>();

        list.add(new RegulationDto(1, "テスト1", "1", "1", "1"));
        list.add(new RegulationDto(1, "テスト1", "2", "2", "2"));
        list.add(new RegulationDto(1, "テスト1", "3", "3", "3"));
        list.add(new RegulationDto(1, "テスト1", "3", "4", "3"));
        list.add(new RegulationDto(1, "テスト1", "3", "5", "3"));

        list.add(new RegulationDto(2, "テスト2", "1", "6", "1"));
        list.add(new RegulationDto(2, "テスト2", "3", "8", "3"));

        list.add(new RegulationDto(3, "テスト3", "2", "10", "2"));
        list.add(new RegulationDto(3, "テスト3", "3", "11", "3"));
        list.add(new RegulationDto(3, "テスト3", "3", "12", "3"));

        return list;
    }

    public List<RegulationListDto> getRegurationList() {
        // 返却リスト
        List<RegulationListDto> result = new ArrayList<>();
        
        // 検索結果を受け取った体
        List<RegulationDto> dtoList = this.createDummyData();

        // 一度PKであるNO、ドキュメント種類IDでソート
        dtoList.sort(Comparator.comparing(RegulationDto::getNo).thenComparing(RegulationDto::getDocTypeId));

        // dtoList.stream().forEach(dto -> log.info(ToStringBuilder.reflectionToString(dto, ToStringStyle.SHORT_PREFIX_STYLE)));

        dtoList.stream().forEach(dto -> {
            // 変換
            RegulationListDto listDto = modelMapper.map(dto, RegulationListDto.class);

            // 未追加
            if(result.stream().filter(item -> item.getNo() == dto.getNo()).findAny().isEmpty()) {
                log.info("追加されていないので変換します");
                // メインファイル
                Optional<RegulationDto> mainFileOptional = dtoList.stream().filter(item -> item.getNo() == dto.getNo() && item.getDocTypeId().equals("1")).findAny();
                if(!mainFileOptional.isEmpty()) {
                    RegulationFileDto mainFile = modelMapper.map(mainFileOptional.get(), RegulationFileDto.class);
                    listDto.setMainFile(mainFile);
                }

                // サブファイル
                Optional<RegulationDto> subFileOptional = dtoList.stream().filter(item -> item.getNo() == dto.getNo() && item.getDocTypeId().equals("2")).findAny();
                if(!subFileOptional.isEmpty()) {
                    RegulationFileDto subFile = modelMapper.map(subFileOptional.get(), RegulationFileDto.class);
                    listDto.setSubFile(subFile);
                }

                // サポートファイル
                List<RegulationDto> supportFileListOrigin = dtoList.stream().filter(item -> item.getNo() == dto.getNo() && item.getDocTypeId().equals("3")).collect(Collectors.toList());
                List<RegulationFileDto> supportFileList = new ArrayList<>();
                supportFileListOrigin.stream().forEach(item -> {
                    RegulationFileDto e = modelMapper.map(item, RegulationFileDto.class);
                    supportFileList.add(e);
                });

                listDto.setSupportFileList(supportFileList);

                log.info("変換後:" + ToStringBuilder.reflectionToString(listDto, ToStringStyle.SHORT_PREFIX_STYLE));

                result.add(listDto);
            }
        });

        result.stream().forEach(item -> log.info(ToStringBuilder.reflectionToString(item, ToStringStyle.SHORT_PREFIX_STYLE)));

        result.sort(Comparator.comparing(RegulationListDto::getNo));

        return result;
    }
}
