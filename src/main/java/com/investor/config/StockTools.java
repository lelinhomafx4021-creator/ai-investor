package com.investor.config;
import com.investor.common.UserContents;
import com.investor.entity.dto.TradeDTO;
import com.investor.entity.po.Holding;
import com.investor.entity.po.Stock;
import com.investor.service.IHoldingService;
import com.investor.service.IStockService;
import com.investor.service.TradeService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.util.List;
/**
 * 股票相关工具函数
 * Spring AI 会自动扫描带 @Description 的 Bean 作为工具使用
 */
@RequiredArgsConstructor
@Component
public class StockTools {
    private final IStockService stockService;
    private  final TradeService tradeService;
    private final IHoldingService holdingService;

    @Tool(description = "根据股票的名称查询股票")
    public String getStockByName(@ToolParam(description = "股票名称，如茅台，腾讯") String name ){
        Stock stock=stockService.lambdaQuery().eq(Stock::getName,name).one();
        if (stock!=null){
            return "股票名称："+stock.getName()+"，股票代码："+stock.getCode()+"，股票价格："+stock.getCurrentPrice();
        }else{
            return "没有查询到此股票的信息";
        }

    }
    @Tool(description = "根据股票的代码查询股票")
    public String getStockByCode(@ToolParam(description = "股票代码，如：600519") String code ){
        Stock stock=stockService.lambdaQuery().eq(Stock::getCode,code).one();
        if (stock!=null){
            return "股票名称："+stock.getName()+"，股票代码："+stock.getCode()+"，股票价格："+stock.getCurrentPrice();
        }else{
            return "没有查询到此股票的信息";
        }

    }
    @Tool(description = "查询所有股票")
    public List<Stock> getAllStocks(){
        return stockService.lambdaQuery().list();
    }

    @Tool(description = "推荐十条最有价值的股票")
    public String recommendStocks(){
        List<Stock> list = stockService.lambdaQuery().orderByDesc(Stock::getCurrentPrice).last("LIMIT 10").list();
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<list.size();i++){
            Stock stock=list.get(i);
            sb.append(i+1)
                    .append(". ")
                    .append("股票名称:"+stock.getName()+",")
                    .append("\n股票代码:"+stock.getCode()+",")
                    .append("\n股票当前价格:"+stock.getCurrentPrice());
        }
        return sb.toString();
    }

    @Tool(description = "根据股票的名称和买入金额进行买入")
    public String buyStockByName(@ToolParam(description = "需要买入的股票代码或者代码，列如：茅台或者MA19922") String codeorName,
                                 @ToolParam(description = "要买入的股票数量") Integer quantity) throws InterruptedException {
        Stock stock = stockService.lambdaQuery().eq(Stock::getCode, codeorName).one();
        if (stock==null) {
            stock = stockService.lambdaQuery().eq(Stock::getName, codeorName).one();
        }
        if (stock==null) {
            return   "没有此股票的信息";
        }
        TradeDTO tradeDTO=new TradeDTO();
        tradeDTO.setCode(stock.getCode());
        tradeDTO.setQuantity(quantity);
        tradeService.buyTrades(tradeDTO);
        return String.format("买入成功，买入%s数量：%d",stock.getName(),quantity);
    }

    @Tool(description = "根据股票的名称和买入金额进行买出")
    public String sellStockByName(@ToolParam(description = "需要卖出的股票代码或者代码，列如：茅台或者MA19922") String codeorName,
                                  @ToolParam(description = "要卖出的股票数量") Integer quantity) throws InterruptedException {
        Stock stock = stockService.lambdaQuery().eq(Stock::getCode, codeorName).one();
        if (stock==null) {
            stock = stockService.lambdaQuery().eq(Stock::getName, codeorName).one();
        }
        if (stock==null) {
            return   "没有此股票的信息";
        }
        TradeDTO tradeDTO=new TradeDTO();
        tradeDTO.setCode(stock.getCode());
        tradeDTO.setQuantity(quantity);
        tradeService.sellTrades(tradeDTO);
        return String.format("卖出成功，卖出%s数量：%d",stock.getName(),quantity);

    }
    
    @Tool(description = "查询用户持仓")
    public String getUserHoldings(){
        List<Holding> holdingList = holdingService.lambdaQuery().eq(Holding::getUserId, UserContents.getUserId()).list();
        if (holdingList==null||holdingList.isEmpty()) {
            return "没有持仓";
        }
        StringBuilder sb=new StringBuilder();
        for (Holding holding : holdingList){
            Stock stock=stockService.lambdaQuery().eq(Stock::getCode,holding.getStockCode()).one();
            sb.append(String.format("股票名称： %s 股票 持有数量：%d 平均成本价格 %s \n",stock.getName(),holding.getQuantity(),holding.getAvgCost()));
        }
        return sb.toString();


    }



}
