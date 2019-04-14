export class ProfileFakeDb {
    public static timeline = {
        activities: [
            {
                user: {
                    name: 'Alice Freeman',
                    avatar: '../../../../content/images/avatars/alice.jpg'
                },
                message: 'started following you.',
                time: '13 mins. ago'
            },
            {
                user: {
                    name: 'Andrew Green',
                    avatar: '../../../../content/images/avatars/andrew.jpg'
                },
                message: 'sent you a message.',
                time: 'June 10,2018'
            },
            {
                user: {
                    name: 'Garry Newman',
                    avatar: '../../../../content/images/avatars/garry.jpg'
                },
                message: 'shared a public post with your group.',
                time: 'June 9,2018'
            },
            {
                user: {
                    name: 'Carl Henderson',
                    avatar: '../../../../content/images/avatars/carl.jpg'
                },
                message: 'wants to play Fallout Shelter with you.',
                time: 'June 8,2018'
            },
            {
                user: {
                    name: 'Jane Dean',
                    avatar: '../../../../content/images/avatars/jane.jpg'
                },
                message: 'started following you.',
                time: 'June 7,2018'
            },
            {
                user: {
                    name: 'Juan Carpenter',
                    avatar: '../../../../content/images/avatars/james.jpg'
                },
                message: 'sent you a message.',
                time: 'June 6,2018'
            },
            {
                user: {
                    name: 'Judith Burton',
                    avatar: '../../../../content/images/avatars/joyce.jpg'
                },
                message: 'shared a photo with you.',
                time: 'June 5,2018'
            },
            {
                user: {
                    name: 'Vincent Munoz',
                    avatar: '../../../../content/images/avatars/vincent.jpg'
                },
                message: 'shared a photo with you.',
                time: 'June 4,2018'
            }
        ],
        posts: [
            {
                user: {
                    name: 'Garry Newman',
                    avatar: '../../../../content/images/avatars/garry.jpg'
                },
                message: 'Remember the place we were talking about the other night? Found it!',
                time: '32 minutes ago',
                type: 'post',
                like: 5,
                share: 21,
                media: {
                    type: 'image',
                    preview: '../../../../content/images/profile/morain-lake.jpg'
                },
                comments: [
                    {
                        user: {
                            name: 'Alice Freeman',
                            avatar: '../../../../content/images/avatars/alice.jpg'
                        },
                        time: 'June 10, 2018',
                        message:
                            'That’s a wonderful place. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce et eleifend ligula. Fusce posuere in sapien ac facilisis. Etiam sit amet justo non felis ornare feugiat.'
                    }
                ]
            },
            {
                user: {
                    name: 'Andrew Green',
                    avatar: '../../../../content/images/avatars/andrew.jpg'
                },
                message: 'Hey, man! Check this, it’s pretty awesome!',
                time: 'June 12, 2018',
                type: 'article',
                like: 98,
                share: 6,
                article: {
                    title: 'Never stop changing!',
                    subtitle: 'John Westrock',
                    excerpt:
                        "John Westrock's new photo album called 'Never stop changing' is published! It features more than 200 photos that will take you right in.",
                    media: {
                        type: 'image',
                        preview: '../../../../content/images/profile/never-stop-changing.jpg'
                    }
                },
                comments: [
                    {
                        user: {
                            name: 'Alice Freeman',
                            avatar: '../../../../content/images/avatars/alice.jpg'
                        },
                        time: 'June 10, 2018',
                        message:
                            'That’s a wonderful place. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce et eleifend ligula. Fusce posuere in sapien ac facilisis. Etiam sit amet justo non felis ornare feugiat.'
                    }
                ]
            },
            {
                user: {
                    name: 'Carl Henderson',
                    avatar: '../../../../content/images/avatars/carl.jpg'
                },
                message:
                    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce et eleifend ligula. Fusce posuere in sapien ac facilisis. Etiam sit amet justo non felis ornare feugiat. Aenean lorem ex, ultrices sit amet ligula sed...',
                time: 'June 10, 2018',
                type: 'something',
                like: 4,
                share: 1
            }
        ]
    };

    public static photosVideos = [
        {
            name: 'June 2018',
            info: '5 Photos',
            media: [
                {
                    type: 'photo',
                    title: 'A Walk Amongst Friends',
                    preview: '../../../../content/images/profile/a-walk-amongst-friends-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Braies Lake',
                    preview: '../../../../content/images/profile/braies-lake-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Fall Glow',
                    preview: '../../../../content/images/profile/fall-glow-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'First Snow',
                    preview: '../../../../content/images/profile/first-snow-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Lago di Braies',
                    preview: '../../../../content/images/profile/lago-di-braies-small.jpg'
                }
            ]
        },
        {
            name: 'May 2018',
            info: '7 Photos, 3 Videos',
            media: [
                {
                    type: 'photo',
                    title: 'Lago di Sorapis',
                    preview: '../../../../content/images/profile/lago-di-sorapis-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Morain Lake',
                    preview: '../../../../content/images/profile/morain-lake-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Never Stop Changing',
                    preview: '../../../../content/images/profile/never-stop-changing-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Reaching',
                    preview: '../../../../content/images/profile/reaching-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Yosemite',
                    preview: '../../../../content/images/profile/yosemite-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'A Walk Amongst Friends',
                    preview: '../../../../content/images/profile/a-walk-amongst-friends-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Braies Lake',
                    preview: '../../../../content/images/profile/braies-lake-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Fall Glow',
                    preview: '../../../../content/images/profile/fall-glow-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'First Snow',
                    preview: '../../../../content/images/profile/first-snow-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Lago di Braies',
                    preview: '../../../../content/images/profile/lago-di-braies-small.jpg'
                }
            ]
        },
        {
            name: 'April 2018',
            info: '7 Photos',
            media: [
                {
                    type: 'photo',
                    title: 'Lago di Sorapis',
                    preview: '../../../../content/images/profile/lago-di-sorapis-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Morain Lake',
                    preview: '../../../../content/images/profile/morain-lake-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Never Stop Changing',
                    preview: '../../../../content/images/profile/never-stop-changing-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Reaching',
                    preview: '../../../../content/images/profile/reaching-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Yosemite',
                    preview: '../../../../content/images/profile/yosemite-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'A Walk Amongst Friends',
                    preview: '../../../../content/images/profile/a-walk-amongst-friends-small.jpg'
                },
                {
                    type: 'photo',
                    title: 'Braies Lake',
                    preview: '../../../../content/images/profile/braies-lake-small.jpg'
                }
            ]
        }
    ];

    public static about = {
        general: {
            gender: 'Male',
            birthday: 'February 30th, 1974',
            locations: ['London, UK', 'New York, USA'],
            about:
                'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eget pharetra felis, sed ullamcorper dui. Sed et elementum neque. Vestibulum pellente viverra ultrices. Etiam justo augue, vehicula ac gravida a, interdum sit amet nisl. Integer vitae nisi id nibh dictum mollis in vitae tortor.'
        },
        work: {
            occupation: 'Developer',
            skills: 'C#, PHP, Javascript, Angular, JS, HTML, CSS',
            jobs: [
                {
                    company: 'Self-Employed',
                    date: '2010 - Now'
                },
                {
                    company: 'Google',
                    date: '2008 - 2010'
                }
            ]
        },
        contact: {
            address:
                'Ut pharetra luctus est quis sodales. Duis nisi tortor, bibendum eget tincidunt, aliquam ac elit. Mauris nec euismod odio.',
            tel: ['+6 555 6600', '+9 555 5255'],
            websites: ['withinpixels.com'],
            emails: ['mail@withinpixels.com', 'mail@creapond.com']
        },
        groups: [
            {
                name: 'Android',
                category: 'Technology',
                members: '1.856.546'
            },
            {
                name: 'Google',
                category: 'Web',
                members: '1.226.121'
            },
            {
                name: 'Fallout',
                category: 'Games',
                members: '526.142'
            }
        ],
        friends: [
            {
                name: 'Garry Newman',
                avatar: '../../../../content/images/avatars/garry.jpg'
            },
            {
                name: 'Carl Henderson',
                avatar: '../../../../content/images/avatars/carl.jpg'
            },
            {
                name: 'Jane Dean',
                avatar: '../../../../content/images/avatars/jane.jpg'
            },
            {
                name: 'Garry Arnold',
                avatar: '../../../../content/images/avatars/garry.jpg'
            },
            {
                name: 'Vincent Munoz',
                avatar: '../../../../content/images/avatars/vincent.jpg'
            },
            {
                name: 'Alice Freeman',
                avatar: '../../../../content/images/avatars/alice.jpg'
            },
            {
                name: 'Andrew Green',
                avatar: '../../../../content/images/avatars/andrew.jpg'
            }
        ]
    };
}
