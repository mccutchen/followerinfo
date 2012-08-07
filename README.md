# followerinfo

A tiny Clojure app to show information about the followers of one or more
Twitter accounts, written to help [@liz_wong][1] see who is following the two
corporate Twitter accounts she works with.

## Installation

Download from [Github][2].

## Usage

    $ java -jar followerinfo-0.1.0-standalone.jar --help
    Usage:

    Switches                               Default  Desc                                                                 
    --------                               -------  ----                                                                 
    -h, --no-help, --help                  false    Show help                                                            
    -v, --no-verbose, --verbose            false    Show detailed info about each follower                               
    -i, --no-intersection, --intersection  true     Show intersection of followers (use --no-intersection to show union) 

## Examples

To print the followers of a single Twitter account (e.g., mine) to `STDOUT` in
CSV format:

    $ java -jar followerinfo-0.1.0-standalone.jar mccutchen | head -5
    hollyhines,52094969
    imsnakes,425546738
    gundenkalanlar,241870191
    laurenkennedy,15519833
    katiegoesbraaap,26127714

To print the intersection of the sets of followers for more than one account
(ie, every user who follows all of the accounts):

    $ java -jar followerinfo-0.1.0-standalone.jar mccutchen liz_wong mykon | head -5
    birdsandfrogs,99022269
    emmleeclaire,310375953
    megansss,17974293
    ashlyprk,574037252
    mcgaddis,120862576

Or to print the union of the sets of followers for more than one account (ie,
every user who follows any of the accounts):

    $ java -jar followerinfo-0.1.0-standalone.jar mccutchen liz_wong mykon --no-intersection | head -5
    AmandaRian,111752930
    CoferLaw,414036071
    oracleofdelhi,17300422
    tBopperz,392122054
    ashlyprk,574037252

Finally, "verbose" information about each account is also available in `JSON`
format:

    $ java -jar followerinfo-0.1.0-standalone.jar mccutchen --verbose | head -1
    {"status":{"text":"@AppDotNet I'm joining the movement and backing http://t.co/dMhr8Ecx. Sign up here: http://t.co/ZNNzzAFM #joinus","retweet_count":0,"coordinates":null,"possibly_sensitive":false,"in_reply_to_status_id_str":null,"contributors":null,"in_reply_to_user_id_str":"104558396","id_str":"232903114804559872","in_reply_to_screen_name":"AppDotNet","retweeted":false,"truncated":false,"created_at":"Tue Aug 07 18:16:44 +0000 2012","geo":null,"place":null,"in_reply_to_status_id":null,"favorited":false,"source":"<a href=\"http://twitter.com/tweetbutton\" rel=\"nofollow\">Tweet Button</a>","id":232903114804559872,"in_reply_to_user_id":104558396},"profile_use_background_image":false,"follow_request_sent":null,"default_profile":false,"profile_sidebar_fill_color":"DFEAFF","protected":false,"following":null,"profile_background_image_url":"http://a0.twimg.com/images/themes/theme1/bg.png","default_profile_image":false,"contributors_enabled":false,"favourites_count":2397,"time_zone":"Central Time (US & Canada)","name":"Brian David Eoff","id_str":"9612722","listed_count":23,"utc_offset":-21600,"profile_link_color":"0000FF","profile_background_tile":false,"location":"Brooklyn","statuses_count":3336,"followers_count":392,"friends_count":626,"created_at":"Tue Oct 23 03:08:11 +0000 2007","lang":"en","profile_sidebar_border_color":"87BC44","url":"http://briandavideoff.org","notifications":null,"profile_background_color":"BEBEBE","geo_enabled":true,"show_all_inline_media":false,"profile_image_url_https":"https://si0.twimg.com/profile_images/1432805083/bde-icon_normal.jpg","is_translator":false,"profile_image_url":"http://a0.twimg.com/profile_images/1432805083/bde-icon_normal.jpg","verified":false,"id":9612722,"profile_background_image_url_https":"https://si0.twimg.com/images/themes/theme1/bg.png","description":"Data Scientist working @bitly, 'On Leave' from Ph.D with @therealcaverlee. Currently living in Brooklyn.","profile_text_color":"000000","screen_name":"bde"}

### Bugs

This is about as far from robust as possible.  I'm sure there are many.


[1]: http://twitter.com/liz_wong
[2]: https://github.com/mccutchen/followerinfo
