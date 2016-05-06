package li.l1t.sic.rest;

import li.l1t.sic.model.Quote;
import li.l1t.sic.service.QuoteService;
import li.l1t.sic.service.QuoteVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Controls REST access to quote votes.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-05-07
 */
@RestController
public class QuoteVoteController {
    @Autowired
    private QuoteVoteService quoteVoteService;
    @Autowired
    private QuoteService quoteService;

    @RequestMapping(value = "/api/quote/by/id/{id}/vote/up")
    public void upVote(@PathVariable("id") int quoteId, Principal user) {
        Quote quote = quoteService.findById(quoteId);
        quoteVoteService.setVote(quote, user, true);
    }

    @RequestMapping(value = "/api/quote/by/id/{id}/vote/down")
    public void downVote(@PathVariable("id") int quoteId, Principal user) {
        Quote quote = quoteService.findById(quoteId);
        quoteVoteService.setVote(quote, user, false);
    }

    @RequestMapping(value = "/api/quote/by/id/{id}/vote/reset")
    public void resetVote(@PathVariable("id") int quoteId, Principal user) {
        Quote quote = quoteService.findById(quoteId);
        quoteVoteService.unsetVote(quote, user);
    }
}
