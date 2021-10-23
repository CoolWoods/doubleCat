import com.doublecat.DoubleCatApplication;
import com.doublecat.entity.mapper.DcGroup;
import com.doublecat.mapper.DcGroupMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author Zongmin
 * @Date Create in 2021/10/23 9:07
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DoubleCatApplication.class)
public class MapperTest {
    @Autowired
    private DcGroupMapper dcGroupMapper;
    @Test
    public void listDcGroup(){
        List<DcGroup> dcGroups = dcGroupMapper.selectAll();
        System.out.println(dcGroups);
    }
}
