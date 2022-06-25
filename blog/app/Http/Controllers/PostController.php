<?php

namespace App\Http\Controllers;
use App\Http\Resources\CommentResource;
use App\Http\Resources\PostResource;

use App\Models\Post;

use Illuminate\Http\Request;

class PostController extends Controller
{
    public function index()
    {
      //fetch 5 posts from database which are active and latest
    //   $posts = Posts::where('active',1)->orderBy('created_at','desc')->paginate(5);
    $posts = Post::with(['author', 'category', 'tags', 'images', 'videos', 'comments'])->orderBy('id', 'desc')->paginate();
      //page heading
      $title = 'Latest Posts';
      //return home.blade.php template from resources/views folder
      return view('blog.index')->withPosts(PostResource::collection($posts))->withTitle($title);
      // return view('home')->withPosts($posts)->withTitle($title);
}

public function index_one()
{
  //fetch 5 posts from database which are active and latest
//   $posts = Posts::where('active',1)->orderBy('created_at','desc')->paginate(5);
$posts = Post::orderBy('id', 'desc')->paginate();
  //page heading
  $title = 'Latest Posts';
  //return home.blade.php template from resources/views folder
  return view('index')->withPosts($posts)->withTitle($title);
  // return view('home')->withPosts($posts)->withTitle($title);
}
}
