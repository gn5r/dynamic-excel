package com.github.gn5r.dynamic.excel.service;

import java.util.List;

import com.github.gn5r.dynamic.excel.common.exception.RestRuntimeException;
import com.github.gn5r.dynamic.excel.dto.FileTypeMstDto;
import com.github.gn5r.dynamic.excel.entity.FileTypeMst;
import com.github.gn5r.dynamic.excel.repository.FileTypeMstDao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileTypeMstService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileTypeMstDao fileTypeMstDao;

    /**
     * 論理削除されたファイル種別マスタを含めた一覧を取得する
     * 
     * @return ファイル種別マスタDtoリスト
     * @see FieTypeMstDto
     */
    public List<FileTypeMstDto> getList() {
        return fileTypeMstDao.selectList();
    }

    /**
     * IDからファイル種別マスタを取得する
     * 
     * @param id ファイル種別マスタID
     * @return ファイル種別マスタDto
     * @see FileTypeMstDto
     */
    public FileTypeMstDto getDetail(Integer id) {
        final FileTypeMst entity = fileTypeMstDao.selectById(id);

        if (entity != null) {
            return modelMapper.map(entity, FileTypeMstDto.class);
        } else {
            throw new RestRuntimeException(HttpStatus.NOT_FOUND, "指定したIDのファイル種別マスタが見つかりません");
        }
    }

    /**
     * ファイル種別マスタを登録する
     * 
     * @param dto ファイル種別マスタDto
     */
    public void regist(FileTypeMstDto dto) {
        final FileTypeMst entity = modelMapper.map(dto, FileTypeMst.class);
        fileTypeMstDao.insert(entity);
    }

    /**
     * ファイル種別マスタを更新する
     * 
     * @param dto ファイル種別マスタDto
     */
    public void update(FileTypeMstDto dto) {
        FileTypeMst entity = fileTypeMstDao.selectById(dto.getId());
        if(entity != null) {
            entity.setTypeName(dto.getTypeName());
            entity.setPrefixPath(dto.getPrefixPath());
            fileTypeMstDao.update(entity);
        } else {
            throw new RestRuntimeException(HttpStatus.NOT_FOUND, "指定したIDのファイル種別マスタが見つかりません");
        }
    }

    /**
     * ファイル種別マスタを論理削除する
     * 
     * @param id ファイル種別マスタID
     */
    public void delete(Integer id) {
        FileTypeMst entity = fileTypeMstDao.selectById(id);
        if (entity != null) {
            entity.setDelFlg(true);
            fileTypeMstDao.update(entity);
        } else {
            throw new RestRuntimeException(HttpStatus.NOT_FOUND, "指定したIDのファイル種別マスタが見つかりません");
        }
    }

    /**
     * ファイル種別マスタを論理削除から復活させる
     * 
     * @param id ファイル種別マスタID
     */
    public void restore(Integer id) {
        FileTypeMst entity = fileTypeMstDao.selectById(id);
        if (entity != null) {
            entity.setDelFlg(false);
            fileTypeMstDao.update(entity);
        } else {
            throw new RestRuntimeException(HttpStatus.NOT_FOUND, "指定したIDのファイル種別マスタが見つかりません");
        }
    }

}
