package tee.finder.qwertee;

import tee.finder.qwertee.domain.Site;
import tee.finder.qwertee.domain.Tee;
import tee.finder.qwertee.domain.Tees;
import tee.finder.qwertee.infrastructure.SiteDto;
import tee.finder.qwertee.infrastructure.TeeDto;
import tee.finder.qwertee.infrastructure.TeesDto;

import java.util.List;
import java.util.stream.Collectors;

public class SiteAdapter {

    public SiteDto toDto(Site site) {
        SiteDto siteDto = new SiteDto();
        siteDto.setName(site.getName());
        siteDto.setUrl(site.getUrl());
        List<TeeDto> teeDtoList = site.getTees().getValue().stream()
                .map(this::toTeeDto)
                .collect(Collectors.toList());
        TeesDto teesDto = new TeesDto();
        teesDto.setValue(teeDtoList);
        siteDto.setTees(teesDto);
        return siteDto;
    }

    private TeeDto toTeeDto(Tee tee) {
        TeeDto teeDto = new TeeDto();
        teeDto.setDate(tee.getDate());
        teeDto.setImage(tee.getImage());
        teeDto.setLink(tee.getLink());
        teeDto.setTitle(tee.getTitle());
        return teeDto;
    }

    public Site toDomain(SiteDto siteDto) {
        Tee[] teeArray = siteDto.getTees().getValue().stream()
                .map(this::toTeeDomain)
                .toArray(size -> new Tee[size]);
        Tees tees = new Tees(teeArray);
        Site site = new Site(siteDto.getName(), siteDto.getUrl(), tees);
        return site;
    }

    public Tee toTeeDomain(TeeDto teeDto) {
        return new Tee(teeDto.getTitle(), teeDto.getLink(), teeDto.getImage(), teeDto.getDate());
    }

}
