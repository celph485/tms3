package tms3.tc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import tms3.tc.model.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class PositionsStore {

    private List<Position> currentPositionList;

    private PositionsStore(){
        log.info("Initializing position store");
        currentPositionList = new ArrayList<>();
    }

    public static PositionsStore getInstance(){
        return new PositionsStore();
    }

    public List<Position> getCurrentPositionList() {
        return currentPositionList;
    }

    public void updatePositions(List<Position> positions){
        log.info("Updating position store with {} positions", CollectionUtils.size(positions));
        if(CollectionUtils.isNotEmpty(positions)){
            this.currentPositionList = Collections.unmodifiableList(positions);
            log.info("Position store updated with {} positions", CollectionUtils.size(positions));
        }
    }
}
