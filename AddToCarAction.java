package com.cds.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import com.cds.po.Game;
import com.cds.po.GameClass;

import com.cds.service.GameClassService;
import com.cds.service.GameService;

@SuppressWarnings("serial")
@Component("addToCarAction")
@Scope("prototype") // 多例，每个请求生成一个新的action
public class AddToCarAction extends ActionSupport {
	private GameService gameService;// 业务逻辑层
	private Game game;

//	private Integer comamount;

	public GameService getGameService() {
		return gameService;
	}

	
	ss
	
	
	
	@Resource
	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	/*public void setComamount(Integer comamount) {
		this.comamount = comamount;
	}

	public Integer getComamount() {
		return comamount;
	}
*/
	

	@SuppressWarnings("unchecked")
	public String execute() throws Exception {

		int gameId = game.getGameId();
		System.out.println("赛事id：" + gameId);
		Map session = (Map) ActionContext.getContext().getSession();

		Game games = gameService.findgameById(gameId);// 获得赛事信息
		if (games.getGameId() == 0) {
			ActionContext.getContext().getSession().put("comnull", "未参加赛事！");
			return "error";
		} else {
			System.out.println("test1");

			List<Game> car = null; // 声明一个报名信息

			System.out.println("test2");
			if (session.get("car") == null) { // 如果session中不存在报名信息
				System.out.println("test3");
				car = new ArrayList<Game>(); // 新建一个ArrayList实例

				car.add(games);// 将赛事添加到报名信息中

				gameService.update(games);
				System.out.println("car1:" + car.size());
			} else {
				System.out.println("test4");
				car = (List<Game>) session.get("car"); // 取得报名信息

				System.out.println("test4.5");

				if (car.size() == 0) { // 如果报名信息为空
					System.out.println("test4.6");
					car.add(games);// 将赛事添加到报名信息中
	
					gameService.update(games);
					System.out.println("car2:" + car.size());

				} else {
					for (int i = car.size(); i > 0; i--) {
						System.out.println("test4.7");

						Game com = car.get(i - 1); // 获取赛事
						System.out.println("test4.8");
						if (com.getGameId() == gameId) { // 赛事已经存在
							System.out.println("test5");
							System.out.println("car2:" + car.size());
						} else { // 赛事不存在
							System.out.println("test5.1");
							car.add(games);// 将赛事添加到报名信息中
			
							gameService.update(games);
							System.out.println("car3:" + car.size());
						}
					}
				}
			}
			

			System.out.println("test7");
			session.put("car", car);// 将报名信息保存在session中
			System.out.println("car:" + car.size());
			return "success";
		}
	}
	@SuppressWarnings("unchecked")
	public String payapply() throws Exception {

		int gameId = game.getGameId();
		System.out.println("赛事id：" + gameId);
		Map session = (Map) ActionContext.getContext().getSession();

		Game games = gameService.findgameById(gameId);// 获得赛事信息
		if (games.getGameId() == 0) {
			ActionContext.getContext().getSession().put("comnull", "未参加赛事！");
			return "error";
		} else {
			System.out.println("test1");

			List<Game> car = null; // 声明一个报名信息

			System.out.println("test2");
			if (session.get("car") == null) { // 如果session中不存在报名信息
				System.out.println("test3");
				car = new ArrayList<Game>(); // 新建一个ArrayList实例

				car.add(games);// 将赛事添加到报名信息中
		
				gameService.update(games);
				System.out.println("car1:" + car.size());
			} else {
				System.out.println("test4");
				car = (List<Game>) session.get("car"); // 取得报名信息

				System.out.println("test4.5");

				if (car.size() == 0) { // 如果报名信息为空
					System.out.println("test4.6");
					car.add(games);// 将赛事添加到报名信息中
					gameService.update(games);
					System.out.println("car2:" + car.size());

				} else {
					for (int i = car.size(); i > 0; i--) {
						System.out.println("test4.7");

						Game com = car.get(i - 1); // 获取赛事
						System.out.println("test4.8");
						if (com.getGameId() == gameId) { // 赛事已经存在
							System.out.println("test5");
							System.out.println("car2:" + car.size());
						} else { // 赛事不存在
							System.out.println("test5.1");
							car.add(games);// 将赛事添加到报名信息中
							gameService.update(games);
							System.out.println("car3:" + car.size());
						}
					}
				}
			}

			System.out.println("test7");
			session.put("car", car);// 将报名信息保存在session中
			System.out.println("car:" + car.size());
			return "payapply";
		}
	}
	@SuppressWarnings("unchecked")
	public String deleteFromCar() {

		int gameId = game.getGameId();
		System.out.println("赛事id：" + gameId);

		Game games = gameService.findgameById(gameId);
		gameService.update(games);

		Map session = ActionContext.getContext().getSession();// 获得session对象
		List<Game> car = (List<Game>) session.get("car");// 取得报名信息
		Iterator<Game> it = car.iterator();
		for (int i = car.size(); it.hasNext(); i--) {
			Game com = it.next();
			if (com.getGameId() == gameId) {
				int index = car.indexOf(com);
				it.remove(); // 这行代码是关键。删除该赛事
			}
		}
		
		session.put("car", car);// 将重新报名信息保存到session中
		System.out.println("car:" + car.size());
		return "delete";
	}

}
