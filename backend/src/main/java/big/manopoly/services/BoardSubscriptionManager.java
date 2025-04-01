package big.manopoly.services;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;

import big.manopoly.data.BoardRepository;
import big.manopoly.dtos.BoardDTO;
import big.manopoly.models.Board;
import big.manopoly.utils.Mapper;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletResponse;

// TODO: consider changing AsyncContext solution to a more robust method later on (such as SseEmitter).
public class BoardSubscriptionManager {
    private static BoardSubscriptionManager instance;

    public static BoardSubscriptionManager instance() {
        if (instance == null) {
            instance = new BoardSubscriptionManager();
        }
        return instance;
    }

    private BoardSubscriptionManager() {
    }

    private final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    private final Map<Long, List<AsyncContext>> subscriptions = new ConcurrentHashMap<>();

    /**
     * Adds a new subscription to be notified if and when a board is updated.
     *
     * @param boardId the id of the board
     * @param sub     the new sub
     */
    public void addSubscription(long boardId, AsyncContext sub) {
        synchronized (subscriptions) {
            if (!this.subscriptions.containsKey(boardId)) {
                this.subscriptions.put(boardId, new ArrayList<>());
            }

            this.subscriptions.get(boardId).add(sub);
        }
    }

    /**
     * Gets all subs interested in a given board
     *
     * @param boardId the id of the board
     * @return an immutable list of subs for that board (can be an empty list)
     */
    private List<AsyncContext> getSubsFor(long boardId) {
        synchronized (subscriptions) {
            if (!subscriptions.containsKey(boardId)) {
                return new ArrayList<>();
            }

            return subscriptions.get(boardId);
        }
    }

    /**
     * Removes the given sub from the subs list for the given board
     *
     * @param boardId the id of the board
     * @param sub     the sub to remove
     */
    private void removeSub(long boardId, AsyncContext sub) {
        synchronized (subscriptions) {
            subscriptions.get(boardId).remove(sub);
        }
    }

    /**
     * Processes all subs for the board with the given id
     *
     * @param boardId the board id
     */
    public void processSubsFor(final long boardId, BoardRepository boardRepository) {
        Board board = boardRepository.getReferenceById(boardId);
        BoardDTO boardDTO = Mapper.toBoardDTO(board);
        THREAD_POOL.submit(() -> {
            // Get the subs which will be notified if the board with the given id is updated
            List<AsyncContext> subs = getSubsFor(boardId);
            System.out.println(subs.size());
            if (!subs.isEmpty()) {
                subs.parallelStream().forEach(sub -> {
                    sub.start(new Runnable() {
                        public void run() {
                            HttpServletResponse response = (HttpServletResponse) sub.getResponse();
                            response.setCharacterEncoding("UTF-8");
                            response.setContentType("text/plain");

                            try {
                                ObjectMapper objectMapper = new ObjectMapper();
                                String json = objectMapper.writeValueAsString(boardDTO);
                                System.out.println(json);

                                PrintWriter writer = response.getWriter();
                                writer.write(json);
                                writer.flush();

                                response.setStatus(HttpServletResponse.SC_OK);
                            } catch (Exception e) {
                                e.printStackTrace();
                                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            }

                            // this might need to be changed
                            sub.complete();
                            removeSub(boardId, sub);
                        }
                    });
                });
            }
        });
    }
}
